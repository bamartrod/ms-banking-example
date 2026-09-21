package com.bamartrod.monolith.cases.application;

import com.bamartrod.monolith.cases.domain.Case;
import com.bamartrod.monolith.cases.ports.out.CaseReader;
import com.bamartrod.monolith.shared.kernel.CorrelationId;
import com.bamartrod.monolith.shared.kernel.Result;
import org.springframework.stereotype.Component;
/**
 * GetCase — component of com.bamartrod.monolith.cases.application bounded context.
 *
 * @author Brandon Martinez
 */

@Component

public class GetCase {
    private final CaseReader reader;
    public GetCase(CaseReader reader) { this.reader = reader; }
    public Result<Case> execute(String clientId, CorrelationId cid) {
        return Result.of(reader.find(clientId, cid), "MSOC_01", "Client " + clientId + " not found");
    }
}
