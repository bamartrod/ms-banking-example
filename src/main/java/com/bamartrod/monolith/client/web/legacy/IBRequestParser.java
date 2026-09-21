package com.bamartrod.monolith.client.web.legacy;

import com.bamartrod.monolith.platform.kernel.DomainError;
import com.bamartrod.monolith.platform.kernel.DomainException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Component;
/**
 * Legacy XML parser for IBRequestParser — anti-corruption for IBRequest XML/CDATA.
 *
 * @author Brandon Martinez
 */


@Component

public class IBRequestParser {

    private final XmlMapper xmlMapper;

    public IBRequestParser(XmlMapper xmlMapper) {
        this.xmlMapper = xmlMapper;
        this.xmlMapper.getFactory().getXMLInputFactory().setProperty("javax.xml.stream.isSupportingExternalEntities", false);
        this.xmlMapper.getFactory().getXMLInputFactory().setProperty("javax.xml.stream.supportDTD", false);
    }

    public record SecureDataPayload(String nit) {}

    public SecureDataPayload extractSecureDataPayload(String rawXml) {
        if (rawXml == null || rawXml.isBlank()) {
            throw new DomainException(new DomainError.Validation("BAD_REQUEST", "Empty XML payload"));
        }
        // Legacy CDATA: <request><![CDATA[<nit>123</nit>]]></request> — Jackson no parsea CDATA anidado como estructura,
        // se extrae el contenido interno antes de deserializar tipado. Sin regex, solo unwrap puntual de CDATA.
        String xmlToParse = rawXml;
        if (rawXml.contains("<![CDATA[")) {
            int s = rawXml.indexOf("<![CDATA[");
            int e = rawXml.indexOf("]]>", s);
            if (s >= 0 && e > s) xmlToParse = rawXml.substring(s + 9, e).trim();
        }
        try {
            LegacyXmlEnvelope env = xmlMapper.readValue(xmlToParse, LegacyXmlEnvelope.class);
            String nit = env.extractNit();
            if (nit != null && nit.trim().contains("<")) {
                try {
                    LegacyXmlEnvelope inner = xmlMapper.readValue(nit.trim(), LegacyXmlEnvelope.class);
                    String innerNit = inner.extractNit();
                    if (innerNit != null && !innerNit.isBlank()) return new SecureDataPayload(innerNit.trim());
                } catch (Exception ignored) {}
            }
            if (nit != null && !nit.isBlank()) return new SecureDataPayload(nit.trim());
            // Fallback Jackson puro para fragmentos simples (<nit>999</nit>, <AA_NIT>123</AA_NIT>) sin regex:
            // Intento mínimo con búsqueda case-insensitive de tags, no Pattern precompilado.
            String fallback = extractViaSimpleTagSearch(xmlToParse);
            if (fallback != null && !fallback.isBlank()) return new SecureDataPayload(fallback.trim());
            throw new IllegalArgumentException("NIT element not found");
        } catch (DomainException e) {
            throw e;
        } catch (Exception e) {
            // Último intento fallback antes de 400, por si xmlToParse era fragmento simple
            String fallback = extractViaSimpleTagSearch(xmlToParse);
            if (fallback != null && !fallback.isBlank()) return new SecureDataPayload(fallback.trim());
            throw new DomainException(new DomainError.Validation("BAD_REQUEST", "Invalid XML payload: " + e.getMessage()));
        }
    }

    private String extractViaSimpleTagSearch(String xml) {
        String lower = xml.toLowerCase();
        for (String tag : new String[]{"aa_nit", "nit"}) {
            String open = "<" + tag;
            int s = lower.indexOf(open);
            while (s >= 0) {
                int gt = xml.indexOf('>', s);
                if (gt < 0) break;
                String close = "</" + tag + ">";
                int e = lower.indexOf(close, gt);
                if (e > gt) {
                    String content = xml.substring(gt + 1, e).trim();
                    // Si contenido aún es XML anidado, extraer recursivamente
                    if (content.contains("<") && content.contains(">")) {
                        String inner = extractViaSimpleTagSearch(content);
                        if (inner != null && !inner.isBlank()) return inner;
                    }
                    if (!content.isBlank() && !content.contains("<")) return content;
                }
                s = lower.indexOf(open, s + 1);
            }
        }
        return null;
    }

    public String toSecureDataResponse(Object data) {
        try { return xmlMapper.writeValueAsString(new SecureDataResponse("OK", data.toString())); }
        catch (Exception e) { return "<response><status>OK</status><data>" + escape(data.toString()) + "</data></response>"; }
    }

    public String toNotFoundResponse(String code) {
        try { return xmlMapper.writeValueAsString(new ErrorResponse(code, "Data does not exist")); }
        catch (Exception e) { return "<response><code>" + escape(code) + "</code><message>Data does not exist</message></response>"; }
    }

    private String escape(String s) { return s.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;"); }
    public record SecureDataResponse(String status, String data) {}
    public record ErrorResponse(String code, String message) {}
}
