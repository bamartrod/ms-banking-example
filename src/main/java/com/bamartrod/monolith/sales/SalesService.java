package com.bamartrod.monolith.sales;

import com.bamartrod.monolith.platform.kernel.CorrelationId;
import com.bamartrod.monolith.platform.persistence.OracleViewReader;
import com.bamartrod.monolith.platform.kernel.Result;
import org.springframework.stereotype.Service;
/**
 * Application service for Sales bounded context. Orchestrates domain logic and persistence.
 *
 * @author Brandon Martinez
 */


@Service

public class SalesService {
    private final OracleViewReader reader;
    public SalesService(OracleViewReader reader){ this.reader=reader;}

    public Result<SalesModels.Territory> findTerritory(String clientId, CorrelationId cid){
        return Result.of(reader.findById(com.bamartrod.monolith.platform.persistence.ViewTarget.SALES, clientId, (id, f) -> SalesModels.ofTerritory(clientId, cid, f)),"MSOC_01","Client "+clientId+" not found");
    }
    public Result<SalesModels.User> findUser(String clientId, CorrelationId cid){
        return Result.of(reader.findById(com.bamartrod.monolith.platform.persistence.ViewTarget.SALES, clientId, (id, f) -> SalesModels.ofUser(clientId, cid, f)),"MSOC_01","Client "+clientId+" not found");
    }
    public Result<SalesModels.Indicators> findIndicators(String clientId, CorrelationId cid){
        return Result.of(reader.findById(com.bamartrod.monolith.platform.persistence.ViewTarget.SALES, clientId, (id, f) -> SalesModels.ofIndicators(clientId, cid, f)),"MSOC_01","Client "+clientId+" not found");
    }
}
