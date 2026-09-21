package com.bamartrod.monolith.platform.integration.siebel;


/**
 * Anti-corruption layer for Siebel integration — SiebelRecord.
 *
 * @author Brandon Martinez
 */
public record SiebelRecord(String clientId, String caseId, String status, String legacyJson){}
