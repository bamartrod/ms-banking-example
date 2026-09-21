package com.bamartrod.monolith.interfaces.sales.api.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * SalesTerritory
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-21T09:36:08.515602825-05:00[America/Bogota]", comments = "Generator version: 7.8.0")
public class SalesTerritory {

  private String clientId;

  private String correlationId;

  private String territoryDescr;

  private String industryDescr;

  public SalesTerritory() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public SalesTerritory(String clientId, String correlationId, String territoryDescr, String industryDescr) {
    this.clientId = clientId;
    this.correlationId = correlationId;
    this.territoryDescr = territoryDescr;
    this.industryDescr = industryDescr;
  }

  public SalesTerritory clientId(String clientId) {
    this.clientId = clientId;
    return this;
  }

  /**
   * Get clientId
   * @return clientId
   */
  @NotNull 
  @Schema(name = "clientId", example = "123", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("clientId")
  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public SalesTerritory correlationId(String correlationId) {
    this.correlationId = correlationId;
    return this;
  }

  /**
   * Get correlationId
   * @return correlationId
   */
  @NotNull 
  @Schema(name = "correlationId", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("correlationId")
  public String getCorrelationId() {
    return correlationId;
  }

  public void setCorrelationId(String correlationId) {
    this.correlationId = correlationId;
  }

  public SalesTerritory territoryDescr(String territoryDescr) {
    this.territoryDescr = territoryDescr;
    return this;
  }

  /**
   * Get territoryDescr
   * @return territoryDescr
   */
  @NotNull 
  @Schema(name = "territoryDescr", example = "Bogotá Norte", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("territoryDescr")
  public String getTerritoryDescr() {
    return territoryDescr;
  }

  public void setTerritoryDescr(String territoryDescr) {
    this.territoryDescr = territoryDescr;
  }

  public SalesTerritory industryDescr(String industryDescr) {
    this.industryDescr = industryDescr;
    return this;
  }

  /**
   * Get industryDescr
   * @return industryDescr
   */
  @NotNull 
  @Schema(name = "industryDescr", example = "Financiera", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("industryDescr")
  public String getIndustryDescr() {
    return industryDescr;
  }

  public void setIndustryDescr(String industryDescr) {
    this.industryDescr = industryDescr;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SalesTerritory salesTerritory = (SalesTerritory) o;
    return Objects.equals(this.clientId, salesTerritory.clientId) &&
        Objects.equals(this.correlationId, salesTerritory.correlationId) &&
        Objects.equals(this.territoryDescr, salesTerritory.territoryDescr) &&
        Objects.equals(this.industryDescr, salesTerritory.industryDescr);
  }

  @Override
  public int hashCode() {
    return Objects.hash(clientId, correlationId, territoryDescr, industryDescr);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SalesTerritory {\n");
    sb.append("    clientId: ").append(toIndentedString(clientId)).append("\n");
    sb.append("    correlationId: ").append(toIndentedString(correlationId)).append("\n");
    sb.append("    territoryDescr: ").append(toIndentedString(territoryDescr)).append("\n");
    sb.append("    industryDescr: ").append(toIndentedString(industryDescr)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

