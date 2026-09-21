package com.bamartrod.monolith.account.web;

import com.bamartrod.monolith.account.AccountCommandService;
import com.bamartrod.monolith.account.AccountMapper;
import com.bamartrod.monolith.account.AccountQueryService;
import com.bamartrod.monolith.interfaces.account.api.AccountApi;
import com.bamartrod.monolith.interfaces.account.api.model.FinancialRequest;
import com.bamartrod.monolith.platform.kernel.CorrelationId;
import com.bamartrod.monolith.platform.web.CorrelationContext;
import com.bamartrod.monolith.platform.web.ResponseDispatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * REST controller for AccountApi bounded context. Handles HTTP requests and delegates to application use cases.
 *
 * @author Brandon Martinez
 */

@RestController
@RequestMapping("/api/v1/account")

public class AccountApiController implements AccountApi {
    private final AccountQueryService query;
    private final AccountCommandService command;
    private final AccountMapper mapper;
    public AccountApiController(AccountQueryService query, AccountCommandService command, AccountMapper mapper){this.query=query; this.command=command; this.mapper=mapper;}
    @Override public ResponseEntity<com.bamartrod.monolith.interfaces.account.api.model.Account> getAccount(String id, String xCorrelationId){
        var cid=CorrelationContext.resolve(xCorrelationId);
        return ResponseDispatcher.dispatch(query.findAccount(id,cid),cid,mapper::toApi);
    }
    @Override public ResponseEntity<com.bamartrod.monolith.interfaces.account.api.model.Expedient> getExpedient(String id, String xCorrelationId){
        var cid=CorrelationContext.resolve(xCorrelationId);
        return ResponseDispatcher.dispatch(query.findExpedient(id,cid),cid,mapper::toApi);
    }
    @Override public ResponseEntity<com.bamartrod.monolith.interfaces.account.api.model.Financial> getFinancial(String id, String xCorrelationId){
        var cid=CorrelationContext.resolve(xCorrelationId);
        return ResponseDispatcher.dispatch(query.findFinancial(id,cid),cid,mapper::toApi);
    }
    @Override public ResponseEntity<Void> extractClient(String id, String xCorrelationId){
        var cid=CorrelationContext.resolve(xCorrelationId);
        var result = command.extractClient(id,cid);
        return switch(result){
            case com.bamartrod.monolith.platform.kernel.Result.Success<Void>(var v) -> ResponseEntity.accepted().header("X-Correlation-Id",cid.value()).build();
            case com.bamartrod.monolith.platform.kernel.Result.Failure<Void>(var err) -> ResponseEntity.status(err.httpStatus()).header("X-Correlation-Id",cid.value()).build();
        };
    }
    @Override public ResponseEntity<Void> updateFinancial(String id, FinancialRequest req, String xCorrelationId){
        var cid=CorrelationContext.resolve(xCorrelationId);
        var result = command.updateFinancial(id,req.getSummary(),cid);
        return switch(result){
            case com.bamartrod.monolith.platform.kernel.Result.Success<Void>(var v) -> ResponseEntity.accepted().header("X-Correlation-Id",cid.value()).build();
            case com.bamartrod.monolith.platform.kernel.Result.Failure<Void>(var err) -> ResponseEntity.status(err.httpStatus()).header("X-Correlation-Id",cid.value()).build();
        };
    }
}
