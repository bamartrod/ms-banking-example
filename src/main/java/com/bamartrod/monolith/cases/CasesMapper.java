package com.bamartrod.monolith.cases;

import com.bamartrod.monolith.interfaces.cases.api.model.Cases;
import com.bamartrod.monolith.interfaces.cases.api.model.CasesOrq;
import com.bamartrod.monolith.interfaces.cases.api.model.CasesSummary;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
/**
 * MapStruct mapper for CasesMapper — converts between domain models and API/persistence DTOs.
 *
 * @author Brandon Martinez
 */

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)

public interface CasesMapper {
    Cases toApi(CasesModels.Cases domain);
    CasesOrq toApi(CasesModels.CasesOrq domain);
    CasesSummary toApi(CasesModels.CasesSummary domain);
}
