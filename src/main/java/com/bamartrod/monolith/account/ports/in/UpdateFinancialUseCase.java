package com.bamartrod.monolith.account.ports.in;

import com.bamartrod.monolith.shared.kernel.CorrelationId;
import com.bamartrod.monolith.shared.kernel.Result;
/**
 * Inbound port (use case) for UpdateFinancialUseCase — application boundary contract.
 *
 * @author Brandon Martinez
 */

public interface UpdateFinancialUseCase {
    Result<Void> execute(String clientId, String summary, CorrelationId cid);
}
