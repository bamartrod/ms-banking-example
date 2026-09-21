package com.bamartrod.monolith.marketing;

import com.bamartrod.monolith.interfaces.marketing.api.model.Campaign;
import com.bamartrod.monolith.interfaces.marketing.api.model.Marketing;
import com.bamartrod.monolith.interfaces.marketing.api.model.Prospect;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-21T09:36:18-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 25.0.4.1 (Arch Linux)"
)
@Component
public class MarketingMapperImpl implements MarketingMapper {

    @Override
    public Marketing toApi(MarketingModels.Marketing domain) {
        if ( domain == null ) {
            return null;
        }

        Marketing marketing = new Marketing();

        marketing.setClientId( domain.clientId() );
        marketing.setCorrelationId( domain.correlationId() );
        marketing.setCampaignId( domain.campaignId() );
        marketing.setSegment( domain.segment() );

        return marketing;
    }

    @Override
    public Prospect toApi(MarketingModels.Prospect domain) {
        if ( domain == null ) {
            return null;
        }

        Prospect prospect = new Prospect();

        prospect.setClientId( domain.clientId() );
        prospect.setCorrelationId( domain.correlationId() );
        prospect.setProspectId( domain.prospectId() );
        prospect.setStage( domain.stage() );

        return prospect;
    }

    @Override
    public Campaign toApi(MarketingModels.Campaign domain) {
        if ( domain == null ) {
            return null;
        }

        Campaign campaign = new Campaign();

        campaign.setClientId( domain.clientId() );
        campaign.setCorrelationId( domain.correlationId() );
        campaign.setCampaignCode( domain.campaignCode() );
        campaign.setDescription( domain.description() );

        return campaign;
    }
}
