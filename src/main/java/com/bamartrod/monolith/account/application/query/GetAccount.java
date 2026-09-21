package com.bamartrod.monolith.account.application.query;

import com.bamartrod.monolith.account.domain.Account;
import com.bamartrod.monolith.account.ports.in.GetAccountUseCase;
import com.bamartrod.monolith.account.ports.out.AccountReader;
import com.bamartrod.monolith.shared.kernel.CorrelationId;
import com.bamartrod.monolith.shared.kernel.Result;
import org.springframework.stereotype.Component;
/**
 * GetAccount — component of com.bamartrod.monolith.account.application.query bounded context.
 *
 * @author Brandon Martinez
 */

@Component

public class GetAccount implements GetAccountUseCase {
    private final AccountReader reader;
    public GetAccount(AccountReader reader) { this.reader = reader; }
    @Override public Result<Account> execute(String clientId, CorrelationId cid) {
        return Result.of(reader.findAccount(clientId, cid), "MSOC_01", "Client " + clientId + " not found");
    }
}
