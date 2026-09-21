package com.bamartrod.monolith.sales.ports.in;

import com.bamartrod.monolith.sales.domain.SalesTerritory;
import com.bamartrod.monolith.shared.kernel.CorrelationId;
import com.bamartrod.monolith.shared.kernel.Result;
/**
 * Inbound port (use case) for GetSalesTerritoryUseCase — application boundary contract.
 *
 * @author Brandon Martinez
 */

public interface GetSalesTerritoryUseCase {
    Result<SalesTerritory> execute(String clientId, CorrelationId cid);
}
