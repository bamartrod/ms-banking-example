package com.bamartrod.monolith.client.web.legacy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
/**
 * Legacy XML envelope for LegacyXmlEnvelope — Jackson XML deserialization model.
 *
 * @author Brandon Martinez
 */


@JacksonXmlRootElement(localName = "IBRequest")
@JsonIgnoreProperties(ignoreUnknown = true)

public record LegacyXmlEnvelope(
        @JacksonXmlProperty(localName = "ContentSections") ContentSections contentSections,
        @JacksonXmlProperty(localName = "CONSULTA") Consulta consulta,
        @JacksonXmlProperty(localName = "request") Request request,
        @JacksonXmlProperty(localName = "nit") String nit,
        @JacksonXmlProperty(localName = "NIT") String nitUpper,
        @JacksonXmlProperty(localName = "AA_NIT") String aaNit
) {
    public record ContentSections(@JacksonXmlProperty(localName = "ContentSection") Section section) {}
    public record Section(@JacksonXmlProperty(localName = "Data") Data data) {}
    public record Data(@JacksonXmlText String payload) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Consulta(
            @JacksonXmlProperty(localName = "BB_TIPO_DOC") String bbTipoDoc,
            @JacksonXmlProperty(localName = "AA_NIT") String aaNit,
            @JacksonXmlProperty(localName = "nit") String nit,
            @JacksonXmlProperty(localName = "NIT") String nitUpper
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Request(
            @JacksonXmlProperty(localName = "nit") String nit,
            @JacksonXmlProperty(localName = "NIT") String nitUpper,
            @JacksonXmlProperty(localName = "AA_NIT") String aaNit,
            @JacksonXmlProperty(localName = "CONSULTA") Consulta consulta
    ) {}

    public String extractNit() {
        if (aaNit != null && !aaNit.isBlank()) return aaNit;
        if (nit != null && !nit.isBlank()) return nit;
        if (nitUpper != null && !nitUpper.isBlank()) return nitUpper;
        if (consulta != null) {
            String v = consulta.aaNit();
            if (v != null && !v.isBlank()) return v;
            v = consulta.nit();
            if (v != null && !v.isBlank()) return v;
            v = consulta.nitUpper();
            if (v != null && !v.isBlank()) return v;
        }
        if (request != null) {
            String v = request.aaNit();
            if (v != null && !v.isBlank()) return v;
            v = request.nit();
            if (v != null && !v.isBlank()) return v;
            v = request.nitUpper();
            if (v != null && !v.isBlank()) return v;
            if (request.consulta() != null) {
                v = request.consulta().aaNit();
                if (v != null && !v.isBlank()) return v;
            }
        }
        if (contentSections != null && contentSections.section() != null && contentSections.section().data() != null) {
            String payload = contentSections.section().data().payload();
            if (payload != null && payload.contains("<")) {
                // CDATA inner XML — delega extracción recursiva
                return payload.trim();
            }
        }
        return null;
    }
}
