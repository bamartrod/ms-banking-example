package com.bamartrod.monolith.account.ports.in;

import com.bamartrod.monolith.shared.kernel.CorrelationId;
import com.bamartrod.monolith.shared.kernel.Result;
/**
 * Inbound port (use case) for ExtractClientUseCase — application boundary contract.
 *
 * @author Brandon Martinez
 */

public interface ExtractClientUseCase {
    Result<Void> execute(String clientId, CorrelationId cid);
}
