package com.bamartrod.monolith.client;

import com.bamartrod.monolith.platform.kernel.CorrelationId;
import com.bamartrod.monolith.platform.kernel.Result;
import com.bamartrod.monolith.platform.persistence.OracleViewReader;
import com.bamartrod.monolith.platform.persistence.ViewTarget;
import org.springframework.stereotype.Service;
/**
 * Application service for Client bounded context. Orchestrates domain logic and persistence.
 *
 * @author Brandon Martinez
 */


@Service

public class ClientService {
    private final OracleViewReader reader;

    public ClientService(OracleViewReader reader) {
        this.reader = reader;
    }

    // — Core client queries — GeneralInfo uses canonical RowMapper (no SQLException introspection)
    public Result<ClientModels.GeneralInfo> findGeneralInfo(String id, CorrelationId cid) {
        return Result.of(reader.query(ViewTarget.CLIENT, id, ClientModels.generalInfoMapper(id, cid)),
                "MSOC_01", "Client " + id + " not found");
    }

    public Result<ClientModels.SecureData> findSecureData(String id, CorrelationId cid) {
        return Result.of(reader.findById(ViewTarget.CLIENT, id, (dbId, field) -> ClientModels.ofSecureData(id, cid, field)),
                "MSOC_01", "Client " + id + " not found");
    }

    // Used by legacy XML adapter (nit payload)
    public Result<ClientModels.SecureData> findSecureDataByNit(String nit, CorrelationId cid) {
        return findSecureData(nit, cid);
    }

    // — Enrichment queries (Strangler Fig: unified under client, ENRICHMENT_VIEW) —
    public Result<ClientModels.Belongs> findBelongsDocument(String id, CorrelationId cid) {
        return Result.of(reader.findById(ViewTarget.ENRICHMENT, id, (dbId, field) -> ClientModels.ofBelongs(id, cid, field)),
                "MSOC_01", "Client " + id + " not found");
    }

    public Result<ClientModels.Destinations> findDestinations(String id, CorrelationId cid) {
        return Result.of(reader.findById(ViewTarget.ENRICHMENT, id, (dbId, field) -> ClientModels.ofDestinations(id, cid, field)),
                "MSOC_01", "Client " + id + " not found");
    }

    public Result<ClientModels.Products> findProducts(String id, CorrelationId cid) {
        return Result.of(reader.findById(ViewTarget.ENRICHMENT, id, (dbId, field) -> ClientModels.ofProducts(id, cid, field)),
                "MSOC_01", "Client " + id + " not found");
    }

    // Legacy aggregate (for XmlMapper compatibility)
    public Result<Client> findClient(String id, CorrelationId cid) {
        return Result.of(reader.findById(ViewTarget.CLIENT, id, (dbId, field) -> ClientModels.ofClient(id, field)),
                "MSOC_01", "Client " + id + " not found");
    }
}
