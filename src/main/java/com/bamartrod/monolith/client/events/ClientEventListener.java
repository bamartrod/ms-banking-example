package com.bamartrod.monolith.client.events;

import com.bamartrod.monolith.client.persistence.ClientJpaRepository;
import com.bamartrod.monolith.platform.events.ClientExtractedEvent;
import com.bamartrod.monolith.platform.events.ClientExtractionFailedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
/**
 * Event listener for ClientEventListener — handles domain events with transactional boundaries.
 *
 * @author Brandon Martinez
 */


@Component

public class ClientEventListener {

    private static final Logger log = LoggerFactory.getLogger(ClientEventListener.class);
    private final ClientJpaRepository repository;
    private final ApplicationEventPublisher publisher;

    public ClientEventListener(ClientJpaRepository repository, ApplicationEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void on(ClientExtractedEvent event) {
        repository.findById(event.clientId()).ifPresentOrElse(entity -> {
            entity.markAsExtracted();
            repository.save(entity);
            log.info("Client {} marked as EXTRACTED via event correlation={}", event.clientId(), event.correlationId().value());
        }, () -> {
            log.warn("ClientExtractedEvent for unknown clientId={} correlation={}", event.clientId(), event.correlationId().value());
            publisher.publishEvent(new ClientExtractionFailedEvent(event.clientId(), event.correlationId(), "NOT_FOUND"));
        });
    }
}
