package com.bamartrod.monolith.cases;

import com.bamartrod.monolith.platform.integration.siebel.SiebelGateway;
import com.bamartrod.monolith.platform.kernel.CorrelationId;
import com.bamartrod.monolith.platform.kernel.Result;
import com.bamartrod.monolith.platform.persistence.OracleViewReader;
import com.bamartrod.monolith.platform.persistence.ViewTarget;
import org.springframework.stereotype.Service;
/**
 * Application service for Cases bounded context. Orchestrates domain logic and persistence.
 *
 * @author Brandon Martinez
 */

@Service

public class CasesService {
    private final OracleViewReader reader; private final SiebelGateway siebel;
    public CasesService(OracleViewReader reader, SiebelGateway siebel){this.reader=reader; this.siebel=siebel;}
    public Result<CasesModels.Cases> findCases(String id, CorrelationId cid){
        return Result.of(reader.findById(ViewTarget.CASES, id, (dbId, field) -> CasesModels.ofCases(id, cid, field)), "MSOC_01","Client "+id+" not found");
    }
    public Result<CasesModels.CasesOrq> findOrq(String id, CorrelationId cid){
        return Result.of(siebel.fetchCase(id).map(r-> new CasesModels.CasesOrq(id,cid.value(),r.caseId(),r.status())), "MSOC_01","Client "+id+" not found");
    }
    public Result<CasesModels.CasesSummary> findSummary(String id, CorrelationId cid){
        return Result.of(reader.findById(ViewTarget.CASES, id, (dbId, field) -> CasesModels.ofSummary(id, cid, field)), "MSOC_01","Client "+id+" not found");
    }
}
