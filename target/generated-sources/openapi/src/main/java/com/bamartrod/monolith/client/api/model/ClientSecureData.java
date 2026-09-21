package com.bamartrod.monolith.client.api.model;

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
 * ClientSecureData
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-21T09:36:10.147188854-05:00[America/Bogota]", comments = "Generator version: 7.8.0")
public class ClientSecureData {

  private String clientId;

  private String correlationId;

  private String maskedAccount;

  private String status;

  public ClientSecureData() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ClientSecureData(String clientId, String correlationId, String maskedAccount, String status) {
    this.clientId = clientId;
    this.correlationId = correlationId;
    this.maskedAccount = maskedAccount;
    this.status = status;
  }

  public ClientSecureData clientId(String clientId) {
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

  public ClientSecureData correlationId(String correlationId) {
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

  public ClientSecureData maskedAccount(String maskedAccount) {
    this.maskedAccount = maskedAccount;
    return this;
  }

  /**
   * Get maskedAccount
   * @return maskedAccount
   */
  @NotNull 
  @Schema(name = "maskedAccount", example = "***1234", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("maskedAccount")
  public String getMaskedAccount() {
    return maskedAccount;
  }

  public void setMaskedAccount(String maskedAccount) {
    this.maskedAccount = maskedAccount;
  }

  public ClientSecureData status(String status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
   */
  @NotNull 
  @Schema(name = "status", example = "ACTIVE", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("status")
  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ClientSecureData clientSecureData = (ClientSecureData) o;
    return Objects.equals(this.clientId, clientSecureData.clientId) &&
        Objects.equals(this.correlationId, clientSecureData.correlationId) &&
        Objects.equals(this.maskedAccount, clientSecureData.maskedAccount) &&
        Objects.equals(this.status, clientSecureData.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(clientId, correlationId, maskedAccount, status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ClientSecureData {\n");
    sb.append("    clientId: ").append(toIndentedString(clientId)).append("\n");
    sb.append("    correlationId: ").append(toIndentedString(correlationId)).append("\n");
    sb.append("    maskedAccount: ").append(toIndentedString(maskedAccount)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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

