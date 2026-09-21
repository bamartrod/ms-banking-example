package com.bamartrod.monolith.sales;

import com.bamartrod.monolith.interfaces.sales.api.model.ProductIndicators;
import com.bamartrod.monolith.interfaces.sales.api.model.SalesTerritory;
import com.bamartrod.monolith.interfaces.sales.api.model.SalesUser;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-21T09:36:19-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 25.0.4.1 (Arch Linux)"
)
@Component
public class SalesMapperImpl implements SalesMapper {

    @Override
    public SalesTerritory toApi(SalesModels.Territory domain) {
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
    public SalesUser toApi(SalesModels.User domain) {
        if ( domain == null ) {
            return null;
        }

        SalesUser salesUser = new SalesUser();

        salesUser.setClientId( domain.clientId() );
        salesUser.setCorrelationId( domain.correlationId() );
        salesUser.setSellerName( domain.sellerName() );
        salesUser.setTerritoryId( domain.territoryId() );

        return salesUser;
    }

    @Override
    public ProductIndicators toApi(SalesModels.Indicators domain) {
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
