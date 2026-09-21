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
 * SalesUser
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-21T09:36:08.515602825-05:00[America/Bogota]", comments = "Generator version: 7.8.0")
public class SalesUser {

  private String clientId;

  private String correlationId;

  private String sellerName;

  private String territoryId;

  public SalesUser() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public SalesUser(String clientId, String correlationId, String sellerName, String territoryId) {
    this.clientId = clientId;
    this.correlationId = correlationId;
    this.sellerName = sellerName;
    this.territoryId = territoryId;
  }

  public SalesUser clientId(String clientId) {
    this.clientId = clientId;
    return this;
  }

  /**
   * Get clientId
   * @return clientId
   */
  @NotNull 
  @Schema(name = "clientId", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("clientId")
  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public SalesUser correlationId(String correlationId) {
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

  public SalesUser sellerName(String sellerName) {
    this.sellerName = sellerName;
    return this;
  }

  /**
   * Get sellerName
   * @return sellerName
   */
  @NotNull 
  @Schema(name = "sellerName", example = "Juan Pérez", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("sellerName")
  public String getSellerName() {
    return sellerName;
  }

  public void setSellerName(String sellerName) {
    this.sellerName = sellerName;
  }

  public SalesUser territoryId(String territoryId) {
    this.territoryId = territoryId;
    return this;
  }

  /**
   * Get territoryId
   * @return territoryId
   */
  @NotNull 
  @Schema(name = "territoryId", example = "TERR-001", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("territoryId")
  public String getTerritoryId() {
    return territoryId;
  }

  public void setTerritoryId(String territoryId) {
    this.territoryId = territoryId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SalesUser salesUser = (SalesUser) o;
    return Objects.equals(this.clientId, salesUser.clientId) &&
        Objects.equals(this.correlationId, salesUser.correlationId) &&
        Objects.equals(this.sellerName, salesUser.sellerName) &&
        Objects.equals(this.territoryId, salesUser.territoryId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(clientId, correlationId, sellerName, territoryId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SalesUser {\n");
    sb.append("    clientId: ").append(toIndentedString(clientId)).append("\n");
    sb.append("    correlationId: ").append(toIndentedString(correlationId)).append("\n");
    sb.append("    sellerName: ").append(toIndentedString(sellerName)).append("\n");
    sb.append("    territoryId: ").append(toIndentedString(territoryId)).append("\n");
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

