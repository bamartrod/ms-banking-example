package com.bamartrod.monolith.marketing.web;

import com.bamartrod.monolith.marketing.MarketingService;
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

class MarketingApiControllerTest {
    private MockMvc mockMvc;
    private OracleViewReader reader;
    @BeforeEach void setUp(){
        reader=mock(OracleViewReader.class);
        var service=new MarketingService(reader);
        var mapper=org.mapstruct.factory.Mappers.getMapper(com.bamartrod.monolith.marketing.MarketingMapper.class);
        var controller=new MarketingApiController(service, mapper);
        mockMvc=MockMvcBuilders.standaloneSetup(controller).build();
        lenient().when(reader.findById(ArgumentMatchers.eq(ViewTarget.MARKETING), ArgumentMatchers.eq("123"), ArgumentMatchers.any()))
                .thenAnswer(inv -> {
                    var fn = (java.util.function.BiFunction<String,String,?>) inv.getArgument(2);
                    return Optional.of(fn.apply("123","camp-123"));
                });
    }
    @Test void getMarketing_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/v1/marketing/123/marketing")).andExpect(status().isOk()).andExpect(jsonPath("$.clientId").value("123"));
    }
    @Test void getMarketing_shouldReturn404WhenNotFound() throws Exception {
        when(reader.findById(ArgumentMatchers.eq(ViewTarget.MARKETING), ArgumentMatchers.eq("999"), ArgumentMatchers.any())).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/v1/marketing/999/marketing")).andExpect(status().isNotFound());
    }
    @Test void getProspect_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/v1/marketing/123/prospect")).andExpect(status().isOk());
    }
    @Test void getCampaign_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/v1/marketing/123/campaign")).andExpect(status().isOk());
    }
}
