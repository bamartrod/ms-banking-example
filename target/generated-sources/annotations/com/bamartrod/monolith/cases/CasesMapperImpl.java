package com.bamartrod.monolith.cases;

import com.bamartrod.monolith.interfaces.cases.api.model.Cases;
import com.bamartrod.monolith.interfaces.cases.api.model.CasesOrq;
import com.bamartrod.monolith.interfaces.cases.api.model.CasesSummary;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-21T09:36:19-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 25.0.4.1 (Arch Linux)"
)
@Component
public class CasesMapperImpl implements CasesMapper {

    @Override
    public Cases toApi(CasesModels.Cases domain) {
        if ( domain == null ) {
            return null;
        }

        Cases cases = new Cases();

        cases.setClientId( domain.clientId() );
        cases.setCorrelationId( domain.correlationId() );
        cases.setCaseId( domain.caseId() );
        cases.setCaseStatus( domain.caseStatus() );

        return cases;
    }

    @Override
    public CasesOrq toApi(CasesModels.CasesOrq domain) {
        if ( domain == null ) {
            return null;
        }

        CasesOrq casesOrq = new CasesOrq();

        casesOrq.setClientId( domain.clientId() );
        casesOrq.setCorrelationId( domain.correlationId() );
        casesOrq.setOrchestrationId( domain.orchestrationId() );
        casesOrq.setCurrentStep( domain.currentStep() );

        return casesOrq;
    }

    @Override
    public CasesSummary toApi(CasesModels.CasesSummary domain) {
        if ( domain == null ) {
            return null;
        }

        CasesSummary casesSummary = new CasesSummary();

        casesSummary.setClientId( domain.clientId() );
        casesSummary.setCorrelationId( domain.correlationId() );
        casesSummary.setSummaryText( domain.summaryText() );
        casesSummary.setOpenCases( domain.openCases() );

        return casesSummary;
    }
}
