package com.bamartrod.monolith.account.application.query;

import com.bamartrod.monolith.account.domain.Financial;
import com.bamartrod.monolith.account.ports.in.GetFinancialUseCase;
import com.bamartrod.monolith.account.ports.out.AccountReader;
import com.bamartrod.monolith.shared.kernel.CorrelationId;
import com.bamartrod.monolith.shared.kernel.Result;
import org.springframework.stereotype.Component;
/**
 * GetFinancial — component of com.bamartrod.monolith.account.application.query bounded context.
 *
 * @author Brandon Martinez
 */

@Component

public class GetFinancial implements GetFinancialUseCase {
    private final AccountReader reader;
    public GetFinancial(AccountReader reader) { this.reader = reader; }
    @Override public Result<Financial> execute(String clientId, CorrelationId cid) {
        return Result.of(reader.findFinancial(clientId, cid), "MSOC_01", "Client " + clientId + " not found");
    }
}
