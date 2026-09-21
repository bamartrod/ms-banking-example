package com.bamartrod.monolith.client.web;

import com.bamartrod.monolith.client.ClientService;
import com.bamartrod.monolith.platform.persistence.OracleViewReader;
import com.bamartrod.monolith.platform.persistence.ViewTarget;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class EnrichmentApiControllerTest {
    private MockMvc mockMvc;
    private OracleViewReader reader;
    @BeforeEach void setUp(){
        reader=mock(OracleViewReader.class);
        var service=new ClientService(reader);
        var mapper=org.mapstruct.factory.Mappers.getMapper(com.bamartrod.monolith.client.ClientMapper.class);
        var controller=new EnrichmentApiController(service, mapper);
        mockMvc=MockMvcBuilders.standaloneSetup(controller).build();
        lenient().when(reader.findById(ArgumentMatchers.eq(ViewTarget.ENRICHMENT), ArgumentMatchers.eq("123"), ArgumentMatchers.any()))
                .thenAnswer(inv -> {
                    var fn = (java.util.function.BiFunction<String,String,?>) inv.getArgument(2);
                    return Optional.of(fn.apply("123","doc-CC"));
                });
        lenient().when(reader.findById(ArgumentMatchers.eq(ViewTarget.CLIENT), ArgumentMatchers.eq("123"), ArgumentMatchers.any()))
                .thenAnswer(inv -> {
                    var fn = (java.util.function.BiFunction<String,String,?>) inv.getArgument(2);
                    return Optional.of(fn.apply("123","doc-CC"));
                });
    }
    @Test void getBelongsDocument_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/v1/client-enrichment/123/a")).andExpect(status().isOk()).andExpect(jsonPath("$.clientId").value("123"));
    }
    @Test void getBelongsDocument_shouldReturn404WhenNotFound() throws Exception {
        when(reader.findById(ArgumentMatchers.eq(ViewTarget.ENRICHMENT), ArgumentMatchers.eq("999"), ArgumentMatchers.any())).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/v1/client-enrichment/999/a")).andExpect(status().isNotFound());
    }
    @Test void getDestinations_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/v1/client-enrichment/123/b")).andExpect(status().isOk());
    }
    @Test void getProducts_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/v1/client-enrichment/123/c")).andExpect(status().isOk());
    }
}
