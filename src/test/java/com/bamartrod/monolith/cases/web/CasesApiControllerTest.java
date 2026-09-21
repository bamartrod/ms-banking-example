package com.bamartrod.monolith.cases.web;

import com.bamartrod.monolith.cases.CasesService;
import com.bamartrod.monolith.platform.integration.siebel.SiebelGateway;
import com.bamartrod.monolith.platform.integration.siebel.SiebelRecord;
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

class CasesApiControllerTest {
    private MockMvc mockMvc;
    private OracleViewReader reader;
    private SiebelGateway siebel;
    @BeforeEach void setUp(){
        reader=mock(OracleViewReader.class);
        siebel=mock(SiebelGateway.class);
        var service=new CasesService(reader,siebel);
        var mapper=org.mapstruct.factory.Mappers.getMapper(com.bamartrod.monolith.cases.CasesMapper.class);
        var controller=new CasesApiController(service, mapper);
        mockMvc=MockMvcBuilders.standaloneSetup(controller).build();
        lenient().when(reader.findById(ArgumentMatchers.eq(ViewTarget.CASES), ArgumentMatchers.eq("123"), ArgumentMatchers.any()))
                .thenAnswer(inv -> {
                    var fn = (java.util.function.BiFunction<String,String,?>) inv.getArgument(2);
                    return Optional.of(fn.apply("123","case-123"));
                });
        when(siebel.fetchCase("123")).thenReturn(Optional.of(new SiebelRecord("123","orq-123","OPEN","json")));
    }
    @Test void getCases_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/v1/cases/123/cases")).andExpect(status().isOk()).andExpect(jsonPath("$.clientId").value("123"));
    }
    @Test void getCasesOrq_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/v1/cases/123/orq")).andExpect(status().isOk());
    }
    @Test void getCasesSummary_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/v1/cases/123/summary")).andExpect(status().isOk());
    }
}
