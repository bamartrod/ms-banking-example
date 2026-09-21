package com.bamartrod.monolith.account.ports.in;

import com.bamartrod.monolith.account.domain.Account;
import com.bamartrod.monolith.shared.kernel.CorrelationId;
import com.bamartrod.monolith.shared.kernel.Result;
/**
 * Inbound port (use case) for GetAccountUseCase — application boundary contract.
 *
 * @author Brandon Martinez
 */

public interface GetAccountUseCase {
    Result<Account> execute(String clientId, CorrelationId cid);
}
