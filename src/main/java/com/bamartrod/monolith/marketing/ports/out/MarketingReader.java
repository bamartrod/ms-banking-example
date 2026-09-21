package com.bamartrod.monolith.marketing.ports.out;

import com.bamartrod.monolith.marketing.domain.Campaign;
import com.bamartrod.monolith.shared.kernel.CorrelationId;
import java.util.Optional;
/**
 * Driven adapter (out) for MarketingReader — reads data from persistence (Oracle view).
 *
 * @author Brandon Martinez
 */

public interface MarketingReader {
    Optional<Campaign> find(String clientId, CorrelationId cid);
}
