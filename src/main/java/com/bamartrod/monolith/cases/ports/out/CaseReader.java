package com.bamartrod.monolith.cases.ports.out;

import com.bamartrod.monolith.cases.domain.Case;
import com.bamartrod.monolith.shared.kernel.CorrelationId;
import java.util.Optional;
/**
 * Driven adapter (out) for CaseReader — reads data from persistence (Oracle view).
 *
 * @author Brandon Martinez
 */

public interface CaseReader {
    Optional<Case> find(String clientId, CorrelationId cid);
}
