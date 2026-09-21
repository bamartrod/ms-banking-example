package com.bamartrod.monolith.platform.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
/**
 * Event listener for FinancialAuditListener — handles domain events with transactional boundaries.
 *
 * @author Brandon Martinez
 */


@Component

public class FinancialAuditListener {

    private static final Logger log = LoggerFactory.getLogger(FinancialAuditListener.class);

    @EventListener
    public void on(FinancialUpdatedEvent event) {
        log.info("Financial update audited clientId={} summary={} correlation={}", event.clientId(), event.summary(), event.correlationId().value());
    }

    @EventListener
    public void onFailed(ClientExtractionFailedEvent event) {
        log.warn("Client extraction failed clientId={} reason={} correlation={}", event.clientId(), event.reason(), event.correlationId().value());
    }
}
