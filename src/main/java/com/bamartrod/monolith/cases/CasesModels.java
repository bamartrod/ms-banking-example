package com.bamartrod.monolith.cases;
public sealed interface CasesModels permits CasesModels.Cases, CasesModels.CasesOrq, CasesModels.CasesSummary {
    String clientId(); String correlationId();
/**
 * Domain models for Cases bounded context — sealed records and factories.
 *
 * @author Brandon Martinez
 */
    record Cases(String clientId, String correlationId, String caseId, String caseStatus) implements CasesModels {}
    record CasesOrq(String clientId, String correlationId, String orchestrationId, String currentStep) implements CasesModels {}
    record CasesSummary(String clientId, String correlationId, String summaryText, int openCases) implements CasesModels {}

    static Cases ofCases(String clientId, com.bamartrod.monolith.platform.kernel.CorrelationId cid, String field) {
        return new Cases(clientId, cid.value(), field, field);
    }
    static CasesSummary ofSummary(String clientId, com.bamartrod.monolith.platform.kernel.CorrelationId cid, String field) {
        return new CasesSummary(clientId, cid.value(), field, 1);
    }
}
