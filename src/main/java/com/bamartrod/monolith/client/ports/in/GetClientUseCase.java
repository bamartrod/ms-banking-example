package com.bamartrod.monolith.client.ports.in;

import com.bamartrod.monolith.client.domain.Client;
import com.bamartrod.monolith.shared.kernel.CorrelationId;
import com.bamartrod.monolith.shared.kernel.Result;
/**
 * Inbound port (use case) for GetClientUseCase — application boundary contract.
 *
 * @author Brandon Martinez
 */

public interface GetClientUseCase {
    Result<Client> execute(String clientId, CorrelationId cid);
}
