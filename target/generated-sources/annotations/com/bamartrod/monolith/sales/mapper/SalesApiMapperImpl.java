package com.bamartrod.monolith.sales.mapper;

import com.bamartrod.monolith.interfaces.sales.api.model.ProductIndicators;
import com.bamartrod.monolith.interfaces.sales.api.model.SalesTerritory;
import com.bamartrod.monolith.sales.domain.SalesUser;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-21T09:36:20-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 25.0.4.1 (Arch Linux)"
)
@Component
public class SalesApiMapperImpl implements SalesApiMapper {

    @Override
    public SalesTerritory toApi(com.bamartrod.monolith.sales.domain.SalesTerritory domain) {
        if ( domain == null ) {
            return null;
        }

        SalesTerritory salesTerritory = new SalesTerritory();

        salesTerritory.setClientId( domain.clientId() );
        salesTerritory.setCorrelationId( domain.correlationId() );
        salesTerritory.setTerritoryDescr( domain.territoryDescr() );
        salesTerritory.setIndustryDescr( domain.industryDescr() );

        return salesTerritory;
    }

    @Override
    public com.bamartrod.monolith.interfaces.sales.api.model.SalesUser toApi(SalesUser domain) {
        if ( domain == null ) {
            return null;
        }

        com.bamartrod.monolith.interfaces.sales.api.model.SalesUser salesUser = new com.bamartrod.monolith.interfaces.sales.api.model.SalesUser();

        salesUser.setClientId( domain.clientId() );
        salesUser.setCorrelationId( domain.correlationId() );
        salesUser.setSellerName( domain.sellerName() );
        salesUser.setTerritoryId( domain.territoryId() );

        return salesUser;
    }

    @Override
    public ProductIndicators toApi(com.bamartrod.monolith.sales.domain.ProductIndicators domain) {
        if ( domain == null ) {
            return null;
        }

        ProductIndicators productIndicators = new ProductIndicators();

        productIndicators.setClientId( domain.clientId() );
        productIndicators.setCorrelationId( domain.correlationId() );
        productIndicators.setProductCode( domain.productCode() );
        productIndicators.setStatus( domain.status() );

        return productIndicators;
    }
}
