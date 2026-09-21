package com.bamartrod.monolith.sales;

import com.bamartrod.monolith.interfaces.sales.api.model.ProductIndicators;
import com.bamartrod.monolith.interfaces.sales.api.model.SalesTerritory;
import com.bamartrod.monolith.interfaces.sales.api.model.SalesUser;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
/**
 * MapStruct mapper for SalesMapper — converts between domain models and API/persistence DTOs.
 *
 * @author Brandon Martinez
 */


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)

public interface SalesMapper {
    SalesTerritory toApi(SalesModels.Territory domain);
    SalesUser toApi(SalesModels.User domain);
    ProductIndicators toApi(SalesModels.Indicators domain);
}
