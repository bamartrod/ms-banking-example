package com.bamartrod.monolith.cases.adapter.out.persistence;

import com.bamartrod.monolith.cases.domain.Case;
import com.bamartrod.monolith.cases.ports.out.CaseReader;
import com.bamartrod.monolith.platform.persistence.ViewTarget;
import com.bamartrod.monolith.shared.kernel.CorrelationId;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;
import java.util.Optional;
/**
 * Driven adapter (out) for OracleCaseReader — reads data from persistence (Oracle view).
 *
 * @author Brandon Martinez
 */

@Component

public class OracleCaseReader implements CaseReader {
    private final JdbcClient jdbc;
    public OracleCaseReader(JdbcClient jdbc) { this.jdbc = jdbc; }
    @Override public Optional<Case> find(String clientId, CorrelationId cid) {
        String sql = "SELECT table_id AS id, field AS data FROM " + ViewTarget.CASES.viewName() + " WHERE table_id = :id";
        return jdbc.sql(sql).param("id", clientId).query((rs,n) -> new Case(clientId, cid.value(), rs.getString("data"), rs.getString("data"))).optional();
    }
}
