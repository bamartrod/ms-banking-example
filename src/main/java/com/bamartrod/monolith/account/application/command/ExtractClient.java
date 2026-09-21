package com.bamartrod.monolith.account.application.command;

import com.bamartrod.monolith.account.ports.in.ExtractClientUseCase;
import com.bamartrod.monolith.platform.events.ClientExtractedEvent;
import com.bamartrod.monolith.platform.ports.ClientExistencePort;
import com.bamartrod.monolith.shared.kernel.CorrelationId;
import com.bamartrod.monolith.shared.kernel.DomainError;
import com.bamartrod.monolith.shared.kernel.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
/**
 * ExtractClient — component of com.bamartrod.monolith.account.application.command bounded context.
 *
 * @author Brandon Martinez
 */

@Component

public class ExtractClient implements ExtractClientUseCase {
    private static final Logger log = LoggerFactory.getLogger(ExtractClient.class);
    private final ApplicationEventPublisher publisher;
    private final ClientExistencePort existencePort;
    public ExtractClient(ApplicationEventPublisher publisher, ClientExistencePort existencePort) {
        this.publisher = publisher; this.existencePort = existencePort;
    }
    @Override @Transactional
    public Result<Void> execute(String clientId, CorrelationId cid) {
        if (clientId == null || clientId.isBlank()) return Result.failure(new DomainError.Validation("MSOC_02", "clientId required"));
        if (!existencePort.exists(clientId)) return Result.failure(new DomainError.NotFound("MSOC_01", "Client " + clientId + " not found for extract"));
        publisher.publishEvent(new ClientExtractedEvent(clientId, new com.bamartrod.monolith.platform.kernel.CorrelationId(cid.value())));
        log.info("ExtractClient published event clientId={} correlation={}", clientId, cid.value());
        return Result.success(null);
    }
}
