package com.bamartrod.monolith.client.application.query;

import com.bamartrod.monolith.client.domain.Client;
import com.bamartrod.monolith.client.ports.in.GetClientUseCase;
import com.bamartrod.monolith.client.ports.out.ClientReader;
import com.bamartrod.monolith.shared.kernel.CorrelationId;
import com.bamartrod.monolith.shared.kernel.Result;
import org.springframework.stereotype.Component;
/**
 * GetClient — component of com.bamartrod.monolith.client.application.query bounded context.
 *
 * @author Brandon Martinez
 */

@Component

public class GetClient implements GetClientUseCase {
    private final ClientReader reader;
    public GetClient(ClientReader reader) { this.reader = reader; }
    @Override public Result<Client> execute(String clientId, CorrelationId cid) {
        return Result.of(reader.find(clientId, cid), "MSOC_01", "Client " + clientId + " not found");
    }
}
