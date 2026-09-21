package com.bamartrod.monolith.sales.application;

import com.bamartrod.monolith.sales.domain.ProductIndicators;
import com.bamartrod.monolith.sales.ports.in.GetProductIndicatorsUseCase;
import com.bamartrod.monolith.sales.ports.out.SalesReader;
import com.bamartrod.monolith.shared.kernel.CorrelationId;
import com.bamartrod.monolith.shared.kernel.Result;
import org.springframework.stereotype.Component;
/**
 * GetProductIndicators — component of com.bamartrod.monolith.sales.application bounded context.
 *
 * @author Brandon Martinez
 */

@Component

public class GetProductIndicators implements GetProductIndicatorsUseCase {

    private final SalesReader reader;

    public GetProductIndicators(SalesReader reader) {
        this.reader = reader;
    }

    @Override
    public Result<ProductIndicators> execute(String clientId, CorrelationId cid) {
        return Result.of(reader.findIndicators(clientId, cid), "MSOC_01", "Client " + clientId + " not found");
    }
}
