package com.bamartrod.monolith.client;

import com.bamartrod.monolith.client.api.model.BelongsDocument;
import com.bamartrod.monolith.client.api.model.ClientGeneral;
import com.bamartrod.monolith.client.api.model.ClientSecureData;
import com.bamartrod.monolith.client.api.model.Destinations;
import com.bamartrod.monolith.client.api.model.Products;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-21T09:36:19-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 25.0.4.1 (Arch Linux)"
)
@Component
public class ClientMapperImpl implements ClientMapper {

    @Override
    public ClientGeneral toApi(ClientModels.GeneralInfo domain) {
        if ( domain == null ) {
            return null;
        }

        ClientGeneral clientGeneral = new ClientGeneral();

        clientGeneral.setClientId( domain.clientId() );
        clientGeneral.setCorrelationId( domain.correlationId() );
        clientGeneral.setDocumentType( domain.documentType() );
        clientGeneral.setDocumentNumber( domain.documentNumber() );
        clientGeneral.setStatus( domain.status() );

        return clientGeneral;
    }

    @Override
    public ClientSecureData toApi(ClientModels.SecureData domain) {
        if ( domain == null ) {
            return null;
        }

        ClientSecureData clientSecureData = new ClientSecureData();

        clientSecureData.setClientId( domain.clientId() );
        clientSecureData.setCorrelationId( domain.correlationId() );
        clientSecureData.setMaskedAccount( domain.maskedAccount() );
        clientSecureData.setStatus( domain.status() );

        return clientSecureData;
    }

    @Override
    public BelongsDocument toApi(ClientModels.Belongs domain) {
        if ( domain == null ) {
            return null;
        }

        BelongsDocument belongsDocument = new BelongsDocument();

        belongsDocument.setClientId( domain.clientId() );
        belongsDocument.setCorrelationId( domain.correlationId() );
        belongsDocument.setDocumentType( domain.documentType() );
        belongsDocument.setBelongsFlag( domain.belongsFlag() );

        return belongsDocument;
    }

    @Override
    public Destinations toApi(ClientModels.Destinations domain) {
        if ( domain == null ) {
            return null;
        }

        Destinations destinations = new Destinations();

        destinations.setClientId( domain.clientId() );
        destinations.setCorrelationId( domain.correlationId() );
        destinations.setDestinationCode( domain.destinationCode() );
        destinations.setActive( domain.active() );

        return destinations;
    }

    @Override
    public Products toApi(ClientModels.Products domain) {
        if ( domain == null ) {
            return null;
        }

        Products products = new Products();

        products.setClientId( domain.clientId() );
        products.setCorrelationId( domain.correlationId() );
        products.setProductCode( domain.productCode() );
        products.setStatus( domain.status() );

        return products;
    }

    @Override
    public com.bamartrod.monolith.interfaces.clientenrichment.api.model.BelongsDocument toLegacyApi(ClientModels.Belongs domain) {
        if ( domain == null ) {
            return null;
        }

        com.bamartrod.monolith.interfaces.clientenrichment.api.model.BelongsDocument belongsDocument = new com.bamartrod.monolith.interfaces.clientenrichment.api.model.BelongsDocument();

        belongsDocument.setClientId( domain.clientId() );
        belongsDocument.setCorrelationId( domain.correlationId() );
        belongsDocument.setDocumentType( domain.documentType() );
        belongsDocument.setBelongsFlag( domain.belongsFlag() );

        return belongsDocument;
    }

    @Override
    public com.bamartrod.monolith.interfaces.clientenrichment.api.model.Destinations toLegacyApi(ClientModels.Destinations domain) {
        if ( domain == null ) {
            return null;
        }

        com.bamartrod.monolith.interfaces.clientenrichment.api.model.Destinations destinations = new com.bamartrod.monolith.interfaces.clientenrichment.api.model.Destinations();

        destinations.setClientId( domain.clientId() );
        destinations.setCorrelationId( domain.correlationId() );
        destinations.setDestinationCode( domain.destinationCode() );
        destinations.setActive( domain.active() );

        return destinations;
    }

    @Override
    public com.bamartrod.monolith.interfaces.clientenrichment.api.model.Products toLegacyApi(ClientModels.Products domain) {
        if ( domain == null ) {
            return null;
        }

        com.bamartrod.monolith.interfaces.clientenrichment.api.model.Products products = new com.bamartrod.monolith.interfaces.clientenrichment.api.model.Products();

        products.setClientId( domain.clientId() );
        products.setCorrelationId( domain.correlationId() );
        products.setProductCode( domain.productCode() );
        products.setStatus( domain.status() );

        return products;
    }
}
