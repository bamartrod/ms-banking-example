package com.bamartrod.monolith.marketing;

import com.bamartrod.monolith.interfaces.marketing.api.model.Campaign;
import com.bamartrod.monolith.interfaces.marketing.api.model.Marketing;
import com.bamartrod.monolith.interfaces.marketing.api.model.Prospect;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
/**
 * MapStruct mapper for MarketingMapper — converts between domain models and API/persistence DTOs.
 *
 * @author Brandon Martinez
 */

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)

public interface MarketingMapper {
    Marketing toApi(MarketingModels.Marketing domain);
    Prospect toApi(MarketingModels.Prospect domain);
    Campaign toApi(MarketingModels.Campaign domain);
}
