package com.bamartrod.monolith.interfaces.cases.api.model;

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
 * Cases
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-21T09:36:07.775291525-05:00[America/Bogota]", comments = "Generator version: 7.8.0")
public class Cases {

  private String clientId;

  private String correlationId;

  private String caseId;

  private String caseStatus;

  public Cases() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Cases(String clientId, String correlationId, String caseId, String caseStatus) {
    this.clientId = clientId;
    this.correlationId = correlationId;
    this.caseId = caseId;
    this.caseStatus = caseStatus;
  }

  public Cases clientId(String clientId) {
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

  public Cases correlationId(String correlationId) {
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

  public Cases caseId(String caseId) {
    this.caseId = caseId;
    return this;
  }

  /**
   * Get caseId
   * @return caseId
   */
  @NotNull 
  @Schema(name = "caseId", example = "CASE-001", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("caseId")
  public String getCaseId() {
    return caseId;
  }

  public void setCaseId(String caseId) {
    this.caseId = caseId;
  }

  public Cases caseStatus(String caseStatus) {
    this.caseStatus = caseStatus;
    return this;
  }

  /**
   * Get caseStatus
   * @return caseStatus
   */
  @NotNull 
  @Schema(name = "caseStatus", example = "OPEN", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("caseStatus")
  public String getCaseStatus() {
    return caseStatus;
  }

  public void setCaseStatus(String caseStatus) {
    this.caseStatus = caseStatus;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Cases cases = (Cases) o;
    return Objects.equals(this.clientId, cases.clientId) &&
        Objects.equals(this.correlationId, cases.correlationId) &&
        Objects.equals(this.caseId, cases.caseId) &&
        Objects.equals(this.caseStatus, cases.caseStatus);
  }

  @Override
  public int hashCode() {
    return Objects.hash(clientId, correlationId, caseId, caseStatus);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Cases {\n");
    sb.append("    clientId: ").append(toIndentedString(clientId)).append("\n");
    sb.append("    correlationId: ").append(toIndentedString(correlationId)).append("\n");
    sb.append("    caseId: ").append(toIndentedString(caseId)).append("\n");
    sb.append("    caseStatus: ").append(toIndentedString(caseStatus)).append("\n");
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

