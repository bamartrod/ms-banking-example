package com.bamartrod.monolith.sales.ports.in;

import com.bamartrod.monolith.sales.domain.ProductIndicators;
import com.bamartrod.monolith.shared.kernel.CorrelationId;
import com.bamartrod.monolith.shared.kernel.Result;
/**
 * Inbound port (use case) for GetProductIndicatorsUseCase — application boundary contract.
 *
 * @author Brandon Martinez
 */

public interface GetProductIndicatorsUseCase {
    Result<ProductIndicators> execute(String clientId, CorrelationId cid);
}
