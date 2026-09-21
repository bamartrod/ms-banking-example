package com.bamartrod.monolith.account.mapper;

import com.bamartrod.monolith.account.domain.Account;
import com.bamartrod.monolith.account.domain.Expedient;
import com.bamartrod.monolith.account.domain.Financial;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
/**
 * MapStruct mapper for AccountApiMapper — converts between domain models and API/persistence DTOs.
 *
 * @author Brandon Martinez
 */

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)

public interface AccountApiMapper {
    com.bamartrod.monolith.interfaces.account.api.model.Account toApi(Account domain);
    com.bamartrod.monolith.interfaces.account.api.model.Expedient toApi(Expedient domain);
    com.bamartrod.monolith.interfaces.account.api.model.Financial toApi(Financial domain);
}
