package com.bamartrod.monolith.marketing.adapter.out.persistence;

import com.bamartrod.monolith.marketing.domain.Campaign;
import com.bamartrod.monolith.marketing.ports.out.MarketingReader;
import com.bamartrod.monolith.platform.persistence.ViewTarget;
import com.bamartrod.monolith.shared.kernel.CorrelationId;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;
import java.util.Optional;
/**
 * Driven adapter (out) for OracleMarketingReader — reads data from persistence (Oracle view).
 *
 * @author Brandon Martinez
 */

@Component

public class OracleMarketingReader implements MarketingReader {
    private final JdbcClient jdbc;
    public OracleMarketingReader(JdbcClient jdbc) { this.jdbc = jdbc; }
    @Override public Optional<Campaign> find(String clientId, CorrelationId cid) {
        String sql = "SELECT table_id AS id, field AS data FROM " + ViewTarget.MARKETING.viewName() + " WHERE table_id = :id";
        return jdbc.sql(sql).param("id", clientId).query((rs,n) -> new Campaign(clientId, cid.value(), rs.getString("data"), rs.getString("data"))).optional();
    }
}
