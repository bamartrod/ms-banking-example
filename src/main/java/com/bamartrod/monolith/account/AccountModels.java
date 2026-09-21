package com.bamartrod.monolith.account;

public sealed interface AccountModels permits AccountModels.Account, AccountModels.Expedient, AccountModels.Financial {
    String clientId();
    String correlationId();
/**
 * Domain models for Account bounded context — sealed records and factories.
 *
 * @author Brandon Martinez
 */
    record Account(String clientId, String correlationId, String accountNumber, String accountType) implements AccountModels {}
    record Expedient(String clientId, String correlationId, String expedientId, String status) implements AccountModels {}
    record Financial(String clientId, String correlationId, String totalBalance, String currency) implements AccountModels {}

    static Account ofAccount(String clientId, com.bamartrod.monolith.platform.kernel.CorrelationId cid, String field) {
        return new Account(clientId, cid.value(), field, field + "-type");
    }
    static Expedient ofExpedient(String clientId, com.bamartrod.monolith.platform.kernel.CorrelationId cid, String field) {
        return new Expedient(clientId, cid.value(), field, "OPEN");
    }
    static Financial ofFinancial(String clientId, com.bamartrod.monolith.platform.kernel.CorrelationId cid, String field) {
        return new Financial(clientId, cid.value(), field, "COP");
    }
}
