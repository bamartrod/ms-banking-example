package com.bamartrod.monolith.account.application.query;

import com.bamartrod.monolith.account.domain.Expedient;
import com.bamartrod.monolith.account.ports.in.GetExpedientUseCase;
import com.bamartrod.monolith.account.ports.out.AccountReader;
import com.bamartrod.monolith.shared.kernel.CorrelationId;
import com.bamartrod.monolith.shared.kernel.Result;
import org.springframework.stereotype.Component;
/**
 * GetExpedient — component of com.bamartrod.monolith.account.application.query bounded context.
 *
 * @author Brandon Martinez
 */

@Component

public class GetExpedient implements GetExpedientUseCase {
    private final AccountReader reader;
    public GetExpedient(AccountReader reader) { this.reader = reader; }
    @Override public Result<Expedient> execute(String clientId, CorrelationId cid) {
        return Result.of(reader.findExpedient(clientId, cid), "MSOC_01", "Client " + clientId + " not found");
    }
}
