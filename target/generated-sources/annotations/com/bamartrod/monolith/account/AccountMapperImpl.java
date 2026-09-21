package com.bamartrod.monolith.account;

import com.bamartrod.monolith.interfaces.account.api.model.Account;
import com.bamartrod.monolith.interfaces.account.api.model.Expedient;
import com.bamartrod.monolith.interfaces.account.api.model.Financial;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-21T09:36:19-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 25.0.4.1 (Arch Linux)"
)
@Component
public class AccountMapperImpl implements AccountMapper {

    @Override
    public Account toApi(AccountModels.Account domain) {
        if ( domain == null ) {
            return null;
        }

        Account account = new Account();

        account.setClientId( domain.clientId() );
        account.setCorrelationId( domain.correlationId() );
        account.setAccountNumber( domain.accountNumber() );
        account.setAccountType( domain.accountType() );

        return account;
    }

    @Override
    public Expedient toApi(AccountModels.Expedient domain) {
        if ( domain == null ) {
            return null;
        }

        Expedient expedient = new Expedient();

        expedient.setClientId( domain.clientId() );
        expedient.setCorrelationId( domain.correlationId() );
        expedient.setExpedientId( domain.expedientId() );
        expedient.setStatus( domain.status() );

        return expedient;
    }

    @Override
    public Financial toApi(AccountModels.Financial domain) {
        if ( domain == null ) {
            return null;
        }

        Financial financial = new Financial();

        financial.setClientId( domain.clientId() );
        financial.setCorrelationId( domain.correlationId() );
        financial.setTotalBalance( domain.totalBalance() );
        financial.setCurrency( domain.currency() );

        return financial;
    }
}
