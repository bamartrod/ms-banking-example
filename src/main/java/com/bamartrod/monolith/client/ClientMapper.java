package com.bamartrod.monolith.client;

import com.bamartrod.monolith.client.api.model.BelongsDocument;
import com.bamartrod.monolith.client.api.model.ClientGeneral;
import com.bamartrod.monolith.client.api.model.ClientSecureData;
import com.bamartrod.monolith.client.api.model.Destinations;
import com.bamartrod.monolith.client.api.model.Products;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
/**
 * MapStruct mapper for ClientMapper — converts between domain models and API/persistence DTOs.
 *
 * @author Brandon Martinez
 */


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)

public interface ClientMapper {

    ClientGeneral toApi(ClientModels.GeneralInfo domain);
    ClientSecureData toApi(ClientModels.SecureData domain);
    BelongsDocument toApi(ClientModels.Belongs domain);
    Destinations toApi(ClientModels.Destinations domain);
    Products toApi(ClientModels.Products domain);

    // Legacy bridge para EnrichmentApiController — mantiene /api/v1/client-enrichment
    com.bamartrod.monolith.interfaces.clientenrichment.api.model.BelongsDocument toLegacyApi(ClientModels.Belongs domain);
    com.bamartrod.monolith.interfaces.clientenrichment.api.model.Destinations toLegacyApi(ClientModels.Destinations domain);
    com.bamartrod.monolith.interfaces.clientenrichment.api.model.Products toLegacyApi(ClientModels.Products domain);
}
