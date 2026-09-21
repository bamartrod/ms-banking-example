package com.bamartrod.monolith.sales.mapper;

import com.bamartrod.monolith.sales.domain.ProductIndicators;
import com.bamartrod.monolith.sales.domain.SalesTerritory;
import com.bamartrod.monolith.sales.domain.SalesUser;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
/**
 * MapStruct mapper for SalesApiMapper — converts between domain models and API/persistence DTOs.
 *
 * @author Brandon Martinez
 */

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)

public interface SalesApiMapper {
    com.bamartrod.monolith.interfaces.sales.api.model.SalesTerritory toApi(SalesTerritory domain);
    com.bamartrod.monolith.interfaces.sales.api.model.SalesUser toApi(SalesUser domain);
    com.bamartrod.monolith.interfaces.sales.api.model.ProductIndicators toApi(ProductIndicators domain);
}
