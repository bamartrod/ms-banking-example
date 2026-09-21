package com.bamartrod.monolith.account;

import com.bamartrod.monolith.interfaces.account.api.model.Account;
import com.bamartrod.monolith.interfaces.account.api.model.Expedient;
import com.bamartrod.monolith.interfaces.account.api.model.Financial;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
/**
 * MapStruct mapper for AccountMapper — converts between domain models and API/persistence DTOs.
 *
 * @author Brandon Martinez
 */

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)

public interface AccountMapper {
    Account toApi(AccountModels.Account domain);
    Expedient toApi(AccountModels.Expedient domain);
    Financial toApi(AccountModels.Financial domain);
}
