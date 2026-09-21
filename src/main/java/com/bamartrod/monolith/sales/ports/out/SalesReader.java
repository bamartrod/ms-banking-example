package com.bamartrod.monolith.sales.ports.out;

import com.bamartrod.monolith.sales.domain.ProductIndicators;
import com.bamartrod.monolith.sales.domain.SalesTerritory;
import com.bamartrod.monolith.sales.domain.SalesUser;
import com.bamartrod.monolith.shared.kernel.CorrelationId;
import java.util.Optional;


/**
 * Driven adapter (out) for SalesReader — reads data from persistence (Oracle view).
 *
 * @author Brandon Martinez
 */
public interface SalesReader {
    Optional<SalesTerritory> findTerritory(String clientId, CorrelationId cid);
    Optional<SalesUser> findUser(String clientId, CorrelationId cid);
    Optional<ProductIndicators> findIndicators(String clientId, CorrelationId cid);
}
