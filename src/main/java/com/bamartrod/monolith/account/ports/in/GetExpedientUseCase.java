package com.bamartrod.monolith.account.ports.in;

import com.bamartrod.monolith.account.domain.Expedient;
import com.bamartrod.monolith.shared.kernel.CorrelationId;
import com.bamartrod.monolith.shared.kernel.Result;
/**
 * Inbound port (use case) for GetExpedientUseCase — application boundary contract.
 *
 * @author Brandon Martinez
 */

public interface GetExpedientUseCase {
    Result<Expedient> execute(String clientId, CorrelationId cid);
}
