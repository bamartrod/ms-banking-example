package com.bamartrod.monolith.sales.adapter.out.persistence;

import com.bamartrod.monolith.platform.persistence.ViewTarget;
import com.bamartrod.monolith.sales.domain.ProductIndicators;
import com.bamartrod.monolith.sales.domain.SalesTerritory;
import com.bamartrod.monolith.sales.domain.SalesUser;
import com.bamartrod.monolith.sales.ports.out.SalesReader;
import com.bamartrod.monolith.shared.kernel.CorrelationId;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;
import java.util.Optional;
/**
 * Driven adapter (out) for OracleSalesReader — reads data from persistence (Oracle view).
 *
 * @author Brandon Martinez
 */


@Component

public class OracleSalesReader implements SalesReader {

    private final JdbcClient jdbc;

    public OracleSalesReader(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    private <T> Optional<T> findById(String clientId, CorrelationId cid, java.util.function.BiFunction<String,String,T> mapper) {
        String sql = "SELECT table_id AS id, field AS data FROM " + ViewTarget.SALES.viewName() + " WHERE table_id = :id";
        return jdbc.sql(sql).param("id", clientId).query((rs, rowNum) -> mapper.apply(rs.getString("id"), rs.getString("data"))).optional();
    }

    @Override
    public Optional<SalesTerritory> findTerritory(String clientId, CorrelationId cid) {
        return findById(clientId, cid, (id, field) -> SalesTerritory.of(clientId, cid, field));
    }

    @Override
    public Optional<SalesUser> findUser(String clientId, CorrelationId cid) {
        return findById(clientId, cid, (id, field) -> SalesUser.of(clientId, cid, field));
    }

    @Override
    public Optional<ProductIndicators> findIndicators(String clientId, CorrelationId cid) {
        return findById(clientId, cid, (id, field) -> ProductIndicators.of(clientId, cid, field));
    }
}
