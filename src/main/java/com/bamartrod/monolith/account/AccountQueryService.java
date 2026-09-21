package com.bamartrod.monolith.account;

import com.bamartrod.monolith.platform.kernel.CorrelationId;
import com.bamartrod.monolith.platform.kernel.Result;
import com.bamartrod.monolith.platform.persistence.OracleViewReader;
import com.bamartrod.monolith.platform.persistence.ViewTarget;
import org.springframework.stereotype.Service;
/**
 * Application service for AccountQuery bounded context. Orchestrates domain logic and persistence.
 *
 * @author Brandon Martinez
 */

@Service

public class AccountQueryService {
    private final OracleViewReader reader;
    public AccountQueryService(OracleViewReader reader){this.reader=reader;}
    public Result<AccountModels.Account> findAccount(String id, CorrelationId cid){
        return Result.of(reader.findById(ViewTarget.ACCOUNT, id, (dbId, field) -> AccountModels.ofAccount(id, cid, field)),"MSOC_01","Client "+id+" not found");
    }
    public Result<AccountModels.Expedient> findExpedient(String id, CorrelationId cid){
        return Result.of(reader.findById(ViewTarget.ACCOUNT, id, (dbId, field) -> AccountModels.ofExpedient(id, cid, field)),"MSOC_01","Client "+id+" not found");
    }
    public Result<AccountModels.Financial> findFinancial(String id, CorrelationId cid){
        return Result.of(reader.findById(ViewTarget.ACCOUNT, id, (dbId, field) -> AccountModels.ofFinancial(id, cid, field)),"MSOC_01","Client "+id+" not found");
    }
}
