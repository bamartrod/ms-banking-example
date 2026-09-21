package com.bamartrod.monolith.account.adapter.in.web;

import com.bamartrod.monolith.account.mapper.AccountApiMapper;
import com.bamartrod.monolith.account.ports.in.*;
import com.bamartrod.monolith.interfaces.account.api.AccountApi;
import com.bamartrod.monolith.interfaces.account.api.model.FinancialRequest;
import com.bamartrod.monolith.shared.kernel.CorrelationId;
import com.bamartrod.monolith.shared.web.CorrelationContext;
import com.bamartrod.monolith.shared.web.ResponseDispatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * REST controller for Account bounded context. Handles HTTP requests and delegates to application use cases.
 *
 * @author Brandon Martinez
 */

@RestController
@RequestMapping("/api/v1/account")
@org.springframework.context.annotation.Profile("hexagonal")

public class AccountController implements AccountApi {
    private final GetAccountUseCase getAccount;
    private final GetExpedientUseCase getExpedient;
    private final GetFinancialUseCase getFinancial;
    private final ExtractClientUseCase extractClient;
    private final UpdateFinancialUseCase updateFinancial;
    private final AccountApiMapper mapper;
    public AccountController(GetAccountUseCase getAccount, GetExpedientUseCase getExpedient, GetFinancialUseCase getFinancial,
                             ExtractClientUseCase extractClient, UpdateFinancialUseCase updateFinancial, AccountApiMapper mapper) {
        this.getAccount = getAccount; this.getExpedient = getExpedient; this.getFinancial = getFinancial;
        this.extractClient = extractClient; this.updateFinancial = updateFinancial; this.mapper = mapper;
    }
    @Override public ResponseEntity<com.bamartrod.monolith.interfaces.account.api.model.Account> getAccount(String id, String xCorrelationId) {
        CorrelationId cid = CorrelationContext.resolve(xCorrelationId);
        return ResponseDispatcher.dispatch(getAccount.execute(id, cid), cid, mapper::toApi);
    }
    @Override public ResponseEntity<com.bamartrod.monolith.interfaces.account.api.model.Expedient> getExpedient(String id, String xCorrelationId) {
        CorrelationId cid = CorrelationContext.resolve(xCorrelationId);
        return ResponseDispatcher.dispatch(getExpedient.execute(id, cid), cid, mapper::toApi);
    }
    @Override public ResponseEntity<com.bamartrod.monolith.interfaces.account.api.model.Financial> getFinancial(String id, String xCorrelationId) {
        CorrelationId cid = CorrelationContext.resolve(xCorrelationId);
        return ResponseDispatcher.dispatch(getFinancial.execute(id, cid), cid, mapper::toApi);
    }
    @Override public ResponseEntity<Void> extractClient(String id, String xCorrelationId) {
        CorrelationId cid = CorrelationContext.resolve(xCorrelationId);
        var result = extractClient.execute(id, cid);
        return switch(result) {
            case com.bamartrod.monolith.shared.kernel.Result.Success<Void>(var v) -> ResponseEntity.accepted().header("X-Correlation-Id", cid.value()).build();
            case com.bamartrod.monolith.shared.kernel.Result.Failure<Void>(var err) -> ResponseEntity.status(err.httpStatus()).header("X-Correlation-Id", cid.value()).build();
        };
    }
    @Override public ResponseEntity<Void> updateFinancial(String id, FinancialRequest req, String xCorrelationId) {
        CorrelationId cid = CorrelationContext.resolve(xCorrelationId);
        var result = updateFinancial.execute(id, req.getSummary(), cid);
        return switch(result) {
            case com.bamartrod.monolith.shared.kernel.Result.Success<Void>(var v) -> ResponseEntity.accepted().header("X-Correlation-Id", cid.value()).build();
            case com.bamartrod.monolith.shared.kernel.Result.Failure<Void>(var err) -> ResponseEntity.status(err.httpStatus()).header("X-Correlation-Id", cid.value()).build();
        };
    }
}
