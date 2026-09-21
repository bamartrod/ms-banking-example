package com.bamartrod.monolith.sales.adapter.in.web;

import com.bamartrod.monolith.interfaces.sales.api.SalesApi;
import com.bamartrod.monolith.interfaces.sales.api.model.ProductIndicators;
import com.bamartrod.monolith.interfaces.sales.api.model.SalesTerritory;
import com.bamartrod.monolith.interfaces.sales.api.model.SalesUser;
import com.bamartrod.monolith.sales.mapper.SalesApiMapper;
import com.bamartrod.monolith.sales.ports.in.GetProductIndicatorsUseCase;
import com.bamartrod.monolith.sales.ports.in.GetSalesTerritoryUseCase;
import com.bamartrod.monolith.sales.ports.in.GetSalesUserUseCase;
import com.bamartrod.monolith.shared.kernel.CorrelationId;
import com.bamartrod.monolith.shared.web.CorrelationContext;
import com.bamartrod.monolith.shared.web.ResponseDispatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * REST controller for Sales bounded context. Handles HTTP requests and delegates to application use cases.
 *
 * @author Brandon Martinez
 */


@RestController
@RequestMapping("/api/v1/sales")
@org.springframework.context.annotation.Profile("hexagonal")

public class SalesController implements SalesApi {

    private final GetSalesTerritoryUseCase getTerritory;
    private final GetSalesUserUseCase getUser;
    private final GetProductIndicatorsUseCase getIndicators;
    private final SalesApiMapper mapper;

    public SalesController(GetSalesTerritoryUseCase getTerritory,
                           GetSalesUserUseCase getUser,
                           GetProductIndicatorsUseCase getIndicators,
                           SalesApiMapper mapper) {
        this.getTerritory = getTerritory;
        this.getUser = getUser;
        this.getIndicators = getIndicators;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<SalesTerritory> getSalesTerritory(String id, String xCorrelationId) {
        CorrelationId cid = CorrelationContext.resolve(xCorrelationId);
        return ResponseDispatcher.dispatch(getTerritory.execute(id, cid), cid, mapper::toApi);
    }

    @Override
    public ResponseEntity<SalesUser> getSalesUser(String id, String xCorrelationId) {
        CorrelationId cid = CorrelationContext.resolve(xCorrelationId);
        return ResponseDispatcher.dispatch(getUser.execute(id, cid), cid, mapper::toApi);
    }

    @Override
    public ResponseEntity<ProductIndicators> getProductIndicators(String id, String xCorrelationId) {
        CorrelationId cid = CorrelationContext.resolve(xCorrelationId);
        return ResponseDispatcher.dispatch(getIndicators.execute(id, cid), cid, mapper::toApi);
    }
}
