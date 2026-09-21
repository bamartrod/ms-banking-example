package com.bamartrod.monolith.client.adapter.out.persistence;

import com.bamartrod.monolith.client.domain.Client;
import com.bamartrod.monolith.client.ports.out.ClientReader;
import com.bamartrod.monolith.platform.persistence.ViewTarget;
import com.bamartrod.monolith.shared.kernel.CorrelationId;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;
import java.util.Optional;
/**
 * Driven adapter (out) for OracleClientReader — reads data from persistence (Oracle view).
 *
 * @author Brandon Martinez
 */

@Component

public class OracleClientReader implements ClientReader {
    private final JdbcClient jdbc;
    public OracleClientReader(JdbcClient jdbc) { this.jdbc = jdbc; }
    @Override public Optional<Client> find(String clientId, CorrelationId cid) {
        String sql = "SELECT * FROM " + ViewTarget.CLIENT.viewName() + " WHERE table_id = :id";
        return jdbc.sql(sql).param("id", clientId).query((rs,n) -> new Client(rs.getString("table_id"), rs.getString("document_type"), rs.getString("document_number"), rs.getString("status"), cid.value())).optional();
    }
}
