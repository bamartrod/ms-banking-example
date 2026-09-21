package com.bamartrod.monolith.account.ports.out;

import com.bamartrod.monolith.account.domain.Account;
import com.bamartrod.monolith.account.domain.Expedient;
import com.bamartrod.monolith.account.domain.Financial;
import com.bamartrod.monolith.shared.kernel.CorrelationId;
import java.util.Optional;
/**
 * Driven adapter (out) for AccountReader — reads data from persistence (Oracle view).
 *
 * @author Brandon Martinez
 */

public interface AccountReader {
    Optional<Account> findAccount(String clientId, CorrelationId cid);
    Optional<Expedient> findExpedient(String clientId, CorrelationId cid);
    Optional<Financial> findFinancial(String clientId, CorrelationId cid);
}
