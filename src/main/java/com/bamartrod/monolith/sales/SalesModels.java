package com.bamartrod.monolith.sales;

import com.bamartrod.monolith.platform.kernel.CorrelationId;

/**
 * Canonical sales models — sealed interface consolidates Territory/User/Indicators as immutable records.
 */
public sealed interface SalesModels permits SalesModels.Territory, SalesModels.User, SalesModels.Indicators {
    String clientId();
    String correlationId();
/**
 * Domain models for Sales bounded context — sealed records and factories.
 *
 * @author Brandon Martinez
 */
    record Territory(String clientId, String correlationId, String territoryDescr, String industryDescr) implements SalesModels {}
    record User(String clientId, String correlationId, String sellerName, String territoryId) implements SalesModels {}
    record Indicators(String clientId, String correlationId, String productCode, String status) implements SalesModels {}

    static Territory ofTerritory(String clientId, CorrelationId cid, String field){ return new Territory(clientId, cid.value(), field, field+"-industry");}
    static User ofUser(String clientId, CorrelationId cid, String field){ return new User(clientId, cid.value(), field, field+"-territory");}
    static Indicators ofIndicators(String clientId, CorrelationId cid, String field){ return new Indicators(clientId, cid.value(), field, "ACTIVE");}
}
