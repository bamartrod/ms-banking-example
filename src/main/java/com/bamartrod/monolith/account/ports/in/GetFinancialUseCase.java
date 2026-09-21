package com.bamartrod.monolith.account.ports.in;

import com.bamartrod.monolith.account.domain.Financial;
import com.bamartrod.monolith.shared.kernel.CorrelationId;
import com.bamartrod.monolith.shared.kernel.Result;
/**
 * Inbound port (use case) for GetFinancialUseCase — application boundary contract.
 *
 * @author Brandon Martinez
 */

public interface GetFinancialUseCase {
    Result<Financial> execute(String clientId, CorrelationId cid);
}
