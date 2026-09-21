package com.bamartrod.monolith.account.application.command;

import com.bamartrod.monolith.account.ports.in.UpdateFinancialUseCase;
import com.bamartrod.monolith.account.ports.out.AccountReader;
import com.bamartrod.monolith.platform.events.FinancialUpdatedEvent;
import com.bamartrod.monolith.shared.kernel.CorrelationId;
import com.bamartrod.monolith.shared.kernel.DomainError;
import com.bamartrod.monolith.shared.kernel.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
/**
 * UpdateFinancial — component of com.bamartrod.monolith.account.application.command bounded context.
 *
 * @author Brandon Martinez
 */

@Component

public class UpdateFinancial implements UpdateFinancialUseCase {
    private static final Logger log = LoggerFactory.getLogger(UpdateFinancial.class);
    private final ApplicationEventPublisher publisher;
    private final AccountReader reader;
    public UpdateFinancial(ApplicationEventPublisher publisher, AccountReader reader) {
        this.publisher = publisher; this.reader = reader;
    }
    @Override @Transactional
    public Result<Void> execute(String clientId, String summary, CorrelationId cid) {
        if (clientId == null || clientId.isBlank()) return Result.failure(new DomainError.Validation("MSOC_02", "clientId required"));
        if (reader.findFinancial(clientId, cid).isEmpty()) return Result.failure(new DomainError.NotFound("MSOC_01", "Account for client " + clientId + " not found"));
        publisher.publishEvent(new FinancialUpdatedEvent(clientId, summary, new com.bamartrod.monolith.platform.kernel.CorrelationId(cid.value())));
        log.info("UpdateFinancial published event clientId={} correlation={}", clientId, cid.value());
        return Result.success(null);
    }
}
