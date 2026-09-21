package com.bamartrod.monolith.sales.application;

import com.bamartrod.monolith.sales.domain.SalesUser;
import com.bamartrod.monolith.sales.ports.in.GetSalesUserUseCase;
import com.bamartrod.monolith.sales.ports.out.SalesReader;
import com.bamartrod.monolith.shared.kernel.CorrelationId;
import com.bamartrod.monolith.shared.kernel.Result;
import org.springframework.stereotype.Component;
/**
 * GetSalesUser — component of com.bamartrod.monolith.sales.application bounded context.
 *
 * @author Brandon Martinez
 */

@Component

public class GetSalesUser implements GetSalesUserUseCase {

    private final SalesReader reader;

    public GetSalesUser(SalesReader reader) {
        this.reader = reader;
    }

    @Override
    public Result<SalesUser> execute(String clientId, CorrelationId cid) {
        return Result.of(reader.findUser(clientId, cid), "MSOC_01", "Client " + clientId + " not found");
    }
}
