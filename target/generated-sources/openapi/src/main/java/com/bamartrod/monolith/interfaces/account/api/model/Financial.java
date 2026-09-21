package com.bamartrod.monolith.interfaces.account.api.model;

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
 * Financial
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-21T09:36:06.016733372-05:00[America/Bogota]", comments = "Generator version: 7.8.0")
public class Financial {

  private String clientId;

  private String correlationId;

  private String totalBalance;

  private String currency;

  public Financial() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Financial(String clientId, String correlationId, String totalBalance, String currency) {
    this.clientId = clientId;
    this.correlationId = correlationId;
    this.totalBalance = totalBalance;
    this.currency = currency;
  }

  public Financial clientId(String clientId) {
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

  public Financial correlationId(String correlationId) {
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

  public Financial totalBalance(String totalBalance) {
    this.totalBalance = totalBalance;
    return this;
  }

  /**
   * Get totalBalance
   * @return totalBalance
   */
  @NotNull 
  @Schema(name = "totalBalance", example = "1520000.00", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("totalBalance")
  public String getTotalBalance() {
    return totalBalance;
  }

  public void setTotalBalance(String totalBalance) {
    this.totalBalance = totalBalance;
  }

  public Financial currency(String currency) {
    this.currency = currency;
    return this;
  }

  /**
   * Get currency
   * @return currency
   */
  @NotNull 
  @Schema(name = "currency", example = "COP", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("currency")
  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Financial financial = (Financial) o;
    return Objects.equals(this.clientId, financial.clientId) &&
        Objects.equals(this.correlationId, financial.correlationId) &&
        Objects.equals(this.totalBalance, financial.totalBalance) &&
        Objects.equals(this.currency, financial.currency);
  }

  @Override
  public int hashCode() {
    return Objects.hash(clientId, correlationId, totalBalance, currency);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Financial {\n");
    sb.append("    clientId: ").append(toIndentedString(clientId)).append("\n");
    sb.append("    correlationId: ").append(toIndentedString(correlationId)).append("\n");
    sb.append("    totalBalance: ").append(toIndentedString(totalBalance)).append("\n");
    sb.append("    currency: ").append(toIndentedString(currency)).append("\n");
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

