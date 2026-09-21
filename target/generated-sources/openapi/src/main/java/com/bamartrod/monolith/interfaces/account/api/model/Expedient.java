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
 * Expedient
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-21T09:36:06.016733372-05:00[America/Bogota]", comments = "Generator version: 7.8.0")
public class Expedient {

  private String clientId;

  private String correlationId;

  private String expedientId;

  private String status;

  public Expedient() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Expedient(String clientId, String correlationId, String expedientId, String status) {
    this.clientId = clientId;
    this.correlationId = correlationId;
    this.expedientId = expedientId;
    this.status = status;
  }

  public Expedient clientId(String clientId) {
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

  public Expedient correlationId(String correlationId) {
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

  public Expedient expedientId(String expedientId) {
    this.expedientId = expedientId;
    return this;
  }

  /**
   * Get expedientId
   * @return expedientId
   */
  @NotNull 
  @Schema(name = "expedientId", example = "EXP-2024-001", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("expedientId")
  public String getExpedientId() {
    return expedientId;
  }

  public void setExpedientId(String expedientId) {
    this.expedientId = expedientId;
  }

  public Expedient status(String status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
   */
  @NotNull 
  @Schema(name = "status", example = "OPEN", requiredMode = Schema.RequiredMode.REQUIRED)
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
    Expedient expedient = (Expedient) o;
    return Objects.equals(this.clientId, expedient.clientId) &&
        Objects.equals(this.correlationId, expedient.correlationId) &&
        Objects.equals(this.expedientId, expedient.expedientId) &&
        Objects.equals(this.status, expedient.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(clientId, correlationId, expedientId, status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Expedient {\n");
    sb.append("    clientId: ").append(toIndentedString(clientId)).append("\n");
    sb.append("    correlationId: ").append(toIndentedString(correlationId)).append("\n");
    sb.append("    expedientId: ").append(toIndentedString(expedientId)).append("\n");
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

