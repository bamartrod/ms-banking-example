package com.bamartrod.monolith.platform.persistence;

import java.util.Optional;
import java.util.function.BiFunction;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
/**
 * Driven adapter (out) for OracleViewReader — reads data from persistence (Oracle view).
 *
 * @author Brandon Martinez
 */


@Repository

public class OracleViewReader {
    private final JdbcClient jdbc;
    public OracleViewReader(JdbcClient jdbc){ this.jdbc=jdbc;}

    public record ClientViewRecord(String id, String data){}

    public <T> Optional<T> findById(ViewTarget target, String id, BiFunction<String, String, T> mapper){
        String sql="SELECT table_id AS id, field AS data FROM "+target.viewName()+" WHERE table_id = :id";
        return jdbc.sql(sql).param("id",id).query((rs, rowNum) -> mapper.apply(rs.getString("id"), rs.getString("data"))).optional();
    }

    /**
     * Proyección rica — permite SELECT * con RowMapper tipado manteniendo allow-list de ViewTarget.
     * Usar para vistas con columnas diferenciadas (ej. CLIENT_VIEW: document_type, document_number, status).
     * ViewTarget evita SQL injection: el nombre de la vista nunca proviene de input de usuario.
     */
    public <T> Optional<T> query(ViewTarget target, String id, RowMapper<T> mapper){
        String sql="SELECT * FROM "+target.viewName()+" WHERE table_id = :id";
        return jdbc.sql(sql).param("id",id).query(mapper).optional();
    }

    public Optional<ClientViewRecord> find(ViewTarget target, String id){
        return findById(target, id, ClientViewRecord::new);
    }

    public Optional<ClientViewRecord> findInView(String viewName, String id){
        // Legacy shim — delega a ViewTarget si coincide, sino allow-list manual (para compat)
        for (ViewTarget v : ViewTarget.values()) if (v.viewName().equals(viewName)) return find(v, id);
        throw new IllegalArgumentException("Unknown view: " + viewName);
    }

    // Delegados tipados — conservados para compatibilidad, ahora delegan a find(ViewTarget)
    public Optional<ClientViewRecord> findAccount(String id){ return find(ViewTarget.ACCOUNT,id);}
    public Optional<ClientViewRecord> findSales(String id){ return find(ViewTarget.SALES,id);}
    public Optional<ClientViewRecord> findCases(String id){ return find(ViewTarget.CASES,id);}
    public Optional<ClientViewRecord> findMarketing(String id){ return find(ViewTarget.MARKETING,id);}
    public Optional<ClientViewRecord> findEnrichment(String id){ return find(ViewTarget.ENRICHMENT,id);}
    public Optional<ClientViewRecord> findClient(String id){ return find(ViewTarget.CLIENT,id);}
}
