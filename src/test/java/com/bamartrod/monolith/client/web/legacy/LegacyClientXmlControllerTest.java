package com.bamartrod.monolith.client.web.legacy;

import com.bamartrod.monolith.client.ClientService;
import com.bamartrod.monolith.platform.persistence.OracleViewReader;
import com.bamartrod.monolith.platform.persistence.ViewTarget;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class LegacyClientXmlControllerTest {
    private MockMvc mockMvc;
    private OracleViewReader reader;

    @BeforeEach void setUp(){
        reader = mock(OracleViewReader.class);
        var service = new ClientService(reader);
        var parser = new IBRequestParser(new XmlMapper());
        var controller = new LegacyClientXmlController(service, parser);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        lenient().when(reader.findById(ArgumentMatchers.eq(ViewTarget.CLIENT), ArgumentMatchers.eq("123"), ArgumentMatchers.any()))
                .thenAnswer(inv -> {
                    java.util.function.BiFunction<String,String,Object> mapper = inv.getArgument(2);
                    return Optional.of(mapper.apply("123","masked-123"));
                });
        lenient().when(reader.query(ArgumentMatchers.eq(ViewTarget.CLIENT), ArgumentMatchers.eq("123"), ArgumentMatchers.any()))
                .thenReturn(Optional.of(new com.bamartrod.monolith.client.ClientModels.GeneralInfo("123", "test-cid", "CC", "12345", "ACTIVE")));
    }

    @Test void secureData_withCdata_shouldReturn200AndCorrelationHeader() throws Exception {
        String xml = "<request><![CDATA[<nit>123</nit>]]></request>";
        mockMvc.perform(post("/ms-client-query/client/secure_data")
                        .contentType(MediaType.APPLICATION_XML_VALUE)
                        .header("X-Correlation-Id","corr-1")
                        .content(xml))
                .andExpect(status().isOk())
                .andExpect(header().string("X-Correlation-Id","corr-1"))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("OK")));
    }

    @Test void secureData_withAttributeAndSpaces_shouldReturn200() throws Exception {
        String xml = "<CONSULTA><AA_NIT type=\"CC\">123</AA_NIT></CONSULTA>";
        mockMvc.perform(post("/ms-client-query/client/secure_data")
                        .contentType(MediaType.APPLICATION_XML_VALUE)
                        .content(xml))
                .andExpect(status().isOk());
    }

    @Test void secureData_notFound_shouldReturn404() throws Exception {
        when(reader.findById(ArgumentMatchers.eq(ViewTarget.CLIENT), ArgumentMatchers.eq("999"), ArgumentMatchers.any())).thenReturn(Optional.empty());
        String xml = "<nit>999</nit>";
        mockMvc.perform(post("/ms-client-query/client/secure_data")
                        .contentType(MediaType.APPLICATION_XML_VALUE)
                        .content(xml))
                .andExpect(status().isNotFound());
    }

    @Test void general_withXml_shouldReturn200() throws Exception {
        String xml = "<nit>123</nit>";
        mockMvc.perform(post("/ms-client-query/client/general")
                        .contentType(MediaType.APPLICATION_XML_VALUE)
                        .content(xml))
                .andExpect(status().isOk());
    }
}
