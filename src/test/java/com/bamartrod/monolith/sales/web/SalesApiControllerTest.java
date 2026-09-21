package com.bamartrod.monolith.sales.web;

import com.bamartrod.monolith.platform.persistence.OracleViewReader;
import com.bamartrod.monolith.platform.persistence.ViewTarget;
import com.bamartrod.monolith.sales.SalesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class SalesApiControllerTest {
    private MockMvc mockMvc;
    private OracleViewReader reader;
    @BeforeEach void setUp(){
        reader=mock(OracleViewReader.class);
        var service=new SalesService(reader);
        var mapper=org.mapstruct.factory.Mappers.getMapper(com.bamartrod.monolith.sales.SalesMapper.class);
        var controller=new SalesApiController(service, mapper);
        mockMvc=MockMvcBuilders.standaloneSetup(controller).build();
        lenient().when(reader.findById(ArgumentMatchers.eq(ViewTarget.SALES), ArgumentMatchers.eq("123"), ArgumentMatchers.any()))
                .thenAnswer(inv -> {
                    var fn = (java.util.function.BiFunction<String,String,?>) inv.getArgument(2);
                    return Optional.of(fn.apply("123","fieldA"));
                });
        lenient().when(reader.findById(ArgumentMatchers.eq(ViewTarget.SALES), ArgumentMatchers.eq("999"), ArgumentMatchers.any()))
                .thenReturn(Optional.empty());
    }
    @Test void getSalesTerritory_shouldReturn200AndHeaders() throws Exception {
        mockMvc.perform(get("/api/v1/sales/123/territory").header("X-Correlation-Id","test-cid"))
                .andExpect(status().isOk()).andExpect(header().string("X-Correlation-Id","test-cid")).andExpect(jsonPath("$.clientId").value("123")).andExpect(jsonPath("$.territoryDescr").value("fieldA"));
    }
    @Test void getSalesTerritory_shouldReturn404WhenNotFound() throws Exception {
        when(reader.findById(ArgumentMatchers.eq(ViewTarget.SALES), ArgumentMatchers.eq("999"), ArgumentMatchers.any())).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/v1/sales/999/territory")).andExpect(status().isNotFound());
    }
    @Test void shouldGenerateCorrelationIdWhenMissing() throws Exception {
        mockMvc.perform(get("/api/v1/sales/123/territory")).andExpect(status().isOk()).andExpect(header().exists("X-Correlation-Id"));
    }
}
