package com.bamartrod.monolith.account.web;

import com.bamartrod.monolith.account.AccountCommandService;
import com.bamartrod.monolith.account.AccountQueryService;
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

class AccountApiControllerTest {
    private MockMvc mockMvc;
    private OracleViewReader reader;
    @BeforeEach void setUp(){
        reader=mock(OracleViewReader.class);
        var query=new AccountQueryService(reader);
        var command=mock(AccountCommandService.class);
        var mapper=org.mapstruct.factory.Mappers.getMapper(com.bamartrod.monolith.account.AccountMapper.class);
        var controller=new AccountApiController(query,command,mapper);
        mockMvc=MockMvcBuilders.standaloneSetup(controller).build();
        lenient().when(reader.findById(ArgumentMatchers.eq(ViewTarget.ACCOUNT), ArgumentMatchers.eq("123"), ArgumentMatchers.any()))
                .thenAnswer(inv -> {
                    var fn = (java.util.function.BiFunction<String,String,?>) inv.getArgument(2);
                    return Optional.of(fn.apply("123","acc-123"));
                });
    }
    @Test void getAccount_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/v1/account/123/account").header("X-Correlation-Id","cid")).andExpect(status().isOk()).andExpect(jsonPath("$.clientId").value("123")).andExpect(jsonPath("$.accountNumber").value("acc-123"));
    }
    @Test void getAccount_shouldReturn404WhenNotFound() throws Exception {
        when(reader.findById(ArgumentMatchers.eq(ViewTarget.ACCOUNT), ArgumentMatchers.eq("999"), ArgumentMatchers.any())).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/v1/account/999/account")).andExpect(status().isNotFound());
    }
    @Test void getExpedient_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/v1/account/123/expedient")).andExpect(status().isOk());
    }
}
