package com.bamartrod.monolith.client.web;

import com.bamartrod.monolith.client.ClientModels;
import com.bamartrod.monolith.client.ClientService;
import com.bamartrod.monolith.platform.kernel.CorrelationId;
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

class ClientApiControllerTest {
    private MockMvc mockMvc;
    private OracleViewReader reader;

    @BeforeEach void setUp(){
        reader = mock(OracleViewReader.class);
        var service = new ClientService(reader);
        var mapper=org.mapstruct.factory.Mappers.getMapper(com.bamartrod.monolith.client.ClientMapper.class);
        var controller = new ClientApiController(service, mapper);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        // GeneralInfo now uses rich RowMapper query (document_type/document_number) — mock query directly
        lenient().when(reader.query(ArgumentMatchers.eq(ViewTarget.CLIENT), ArgumentMatchers.eq("123"), ArgumentMatchers.any()))
                .thenReturn(Optional.of(new ClientModels.GeneralInfo("123", "test-cid", "CC", "12345", "ACTIVE")));
        // SecureData and enrichment still via findById
        lenient().when(reader.findById(ArgumentMatchers.eq(ViewTarget.CLIENT), ArgumentMatchers.eq("123"), ArgumentMatchers.any()))
                .thenAnswer(inv -> {
                    java.util.function.BiFunction<String,String,Object> fn = inv.getArgument(2);
                    return Optional.of(fn.apply("123", "masked-123"));
                });
        lenient().when(reader.findById(ArgumentMatchers.eq(ViewTarget.ENRICHMENT), ArgumentMatchers.eq("123"), ArgumentMatchers.any()))
                .thenAnswer(inv -> {
                    java.util.function.BiFunction<String,String,Object> fn = inv.getArgument(2);
                    return Optional.of(fn.apply("123", "enrich-data"));
                });
    }

    @Test void getGeneral_shouldReturn200WithSpecificDto() throws Exception {
        mockMvc.perform(get("/api/v1/clients/123/general"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientId").value("123"))
                .andExpect(jsonPath("$.documentNumber").exists())
                .andExpect(jsonPath("$.documentType").exists());
    }

    @Test void getSecureData_shouldReturn200WithSpecificDto() throws Exception {
        mockMvc.perform(get("/api/v1/clients/123/secure-data"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientId").value("123"))
                .andExpect(jsonPath("$.maskedAccount").exists());
    }

    @Test void getGeneral_shouldReturn404WhenNotFound() throws Exception {
        when(reader.query(ArgumentMatchers.eq(ViewTarget.CLIENT), ArgumentMatchers.eq("999"), ArgumentMatchers.any())).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/v1/clients/999/general")).andExpect(status().isNotFound());
    }

    @Test void getBelongs_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/v1/clients/123/belongs")).andExpect(status().isOk());
    }

    @Test void getDestinations_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/v1/clients/123/destinations")).andExpect(status().isOk());
    }

    @Test void getProducts_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/v1/clients/123/products")).andExpect(status().isOk());
    }
}
