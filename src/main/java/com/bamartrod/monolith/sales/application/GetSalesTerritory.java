package com.bamartrod.monolith.sales.application;

import com.bamartrod.monolith.sales.domain.SalesTerritory;
import com.bamartrod.monolith.sales.ports.in.GetSalesTerritoryUseCase;
import com.bamartrod.monolith.sales.ports.out.SalesReader;
import com.bamartrod.monolith.shared.kernel.CorrelationId;
import com.bamartrod.monolith.shared.kernel.Result;
import org.springframework.stereotype.Component;
/**
 * GetSalesTerritory — component of com.bamartrod.monolith.sales.application bounded context.
 *
 * @author Brandon Martinez
 */


@Component

public class GetSalesTerritory implements GetSalesTerritoryUseCase {

    private final SalesReader reader;

    public GetSalesTerritory(SalesReader reader) {
        this.reader = reader;
    }

    @Override
    public Result<SalesTerritory> execute(String clientId, CorrelationId cid) {
        return Result.of(reader.findTerritory(clientId, cid), "MSOC_01", "Client " + clientId + " not found");
    }
}
