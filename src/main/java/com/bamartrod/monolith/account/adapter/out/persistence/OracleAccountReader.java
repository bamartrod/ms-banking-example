package com.bamartrod.monolith.account.adapter.out.persistence;

import com.bamartrod.monolith.account.domain.Account;
import com.bamartrod.monolith.account.domain.Expedient;
import com.bamartrod.monolith.account.domain.Financial;
import com.bamartrod.monolith.account.ports.out.AccountReader;
import com.bamartrod.monolith.platform.persistence.ViewTarget;
import com.bamartrod.monolith.shared.kernel.CorrelationId;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;
import java.util.Optional;
/**
 * Driven adapter (out) for OracleAccountReader — reads data from persistence (Oracle view).
 *
 * @author Brandon Martinez
 */

@Component

public class OracleAccountReader implements AccountReader {
    private final JdbcClient jdbc;
    public OracleAccountReader(JdbcClient jdbc) { this.jdbc = jdbc; }
    private <T> Optional<T> find(String clientId, CorrelationId cid, java.util.function.BiFunction<String,String,T> mapper) {
        String sql = "SELECT table_id AS id, field AS data FROM " + ViewTarget.ACCOUNT.viewName() + " WHERE table_id = :id";
        return jdbc.sql(sql).param("id", clientId).query((rs, n) -> mapper.apply(rs.getString("id"), rs.getString("data"))).optional();
    }
    @Override public Optional<Account> findAccount(String clientId, CorrelationId cid) {
        return find(clientId, cid, (id, f) -> Account.of(clientId, cid, f, f + "-type"));
    }
    @Override public Optional<Expedient> findExpedient(String clientId, CorrelationId cid) {
        return find(clientId, cid, (id, f) -> Expedient.of(clientId, cid, f));
    }
    @Override public Optional<Financial> findFinancial(String clientId, CorrelationId cid) {
        return find(clientId, cid, (id, f) -> Financial.of(clientId, cid, f));
    }
}
