package com.bamartrod.monolith.account;

import com.bamartrod.monolith.platform.events.ClientExtractedEvent;
import com.bamartrod.monolith.platform.events.FinancialUpdatedEvent;
import com.bamartrod.monolith.platform.kernel.CorrelationId;
import com.bamartrod.monolith.platform.kernel.DomainError;
import com.bamartrod.monolith.platform.kernel.Result;
import com.bamartrod.monolith.platform.persistence.OracleViewReader;
import com.bamartrod.monolith.platform.persistence.ViewTarget;
import com.bamartrod.monolith.platform.ports.ClientExistencePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * Application service for AccountCommand bounded context. Orchestrates domain logic and persistence.
 *
 * @author Brandon Martinez
 */


@Service

public class AccountCommandService {

    private static final Logger log = LoggerFactory.getLogger(AccountCommandService.class);
    private final ApplicationEventPublisher publisher;
    private final OracleViewReader viewReader;
    private final ClientExistencePort clientExistencePort;

    public AccountCommandService(ApplicationEventPublisher publisher, OracleViewReader viewReader, ClientExistencePort clientExistencePort) {
        this.publisher = publisher;
        this.viewReader = viewReader;
        this.clientExistencePort = clientExistencePort;
    }

    @Transactional
    public Result<Void> extractClient(String clientId, CorrelationId cid) {
        try {
            if (clientId == null || clientId.isBlank()) {
                return Result.failure(new DomainError.Validation("MSOC_02", "clientId required"));
            }
            // Verificación desacoplada vía puerto (account -> platform <- client), no ViewTarget.CLIENT directo.
            // Evita asumir esquema de lectura de client y elimina race entre validación y evento.
            if (!clientExistencePort.exists(clientId)) {
                return Result.failure(new DomainError.NotFound("MSOC_01", "Client " + clientId + " not found for extract"));
            }
            publisher.publishEvent(new ClientExtractedEvent(clientId, cid));
            log.info("extractClient published event clientId={} correlation={}", clientId, cid.value());
            return Result.success(null);
        } catch (Exception e) {
            log.error("extractClient failed clientId={} cid={}", clientId, cid.value(), e);
            return Result.failure(new DomainError.Conflict("MSOC_02", e.getMessage()));
        }
    }

    @Transactional
    public Result<Void> updateFinancial(String clientId, String summary, CorrelationId cid) {
        try {
            if (clientId == null || clientId.isBlank()) {
                return Result.failure(new DomainError.Validation("MSOC_02", "clientId required"));
            }
            // ACCOUNT_VIEW es de solo lectura — validamos existencia y publicamos evento auditado.
            // Un futuro write-model escuchará FinancialUpdatedEvent; hoy es trazabilidad operativa.
            var exists = viewReader.findById(ViewTarget.ACCOUNT, clientId, (a, b) -> true).isPresent();
            if (!exists) {
                return Result.failure(new DomainError.NotFound("MSOC_01", "Account for client " + clientId + " not found"));
            }
            publisher.publishEvent(new FinancialUpdatedEvent(clientId, summary, cid));
            log.info("updateFinancial published event clientId={} correlation={} summary={}", clientId, cid.value(), summary);
            return Result.success(null);
        } catch (Exception e) {
            log.error("updateFinancial failed clientId={} cid={}", clientId, cid.value(), e);
            return Result.failure(new DomainError.Conflict("MSOC_02", e.getMessage()));
        }
    }
}
