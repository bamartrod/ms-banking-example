package com.bamartrod.monolith.client.persistence;

import com.bamartrod.monolith.platform.persistence.OracleViewReader;
import com.bamartrod.monolith.platform.persistence.ViewTarget;
import com.bamartrod.monolith.platform.ports.ClientExistencePort;
import org.springframework.stereotype.Component;
/**
 * ClientExistenceAdapter — component of com.bamartrod.monolith.client.persistence bounded context.
 *
 * @author Brandon Martinez
 */


@Component

public class ClientExistenceAdapter implements ClientExistencePort {

    private final OracleViewReader viewReader;

    public ClientExistenceAdapter(OracleViewReader viewReader) {
        this.viewReader = viewReader;
    }

    @Override
    public boolean exists(String clientId) {
        return viewReader.findById(ViewTarget.CLIENT, clientId, (a, b) -> true).isPresent();
    }
}
