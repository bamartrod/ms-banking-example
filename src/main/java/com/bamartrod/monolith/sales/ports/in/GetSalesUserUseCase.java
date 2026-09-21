package com.bamartrod.monolith.sales.ports.in;

import com.bamartrod.monolith.sales.domain.SalesUser;
import com.bamartrod.monolith.shared.kernel.CorrelationId;
import com.bamartrod.monolith.shared.kernel.Result;
/**
 * Inbound port (use case) for GetSalesUserUseCase — application boundary contract.
 *
 * @author Brandon Martinez
 */

public interface GetSalesUserUseCase {
    Result<SalesUser> execute(String clientId, CorrelationId cid);
}
