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
 * CasesSummary
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-21T09:36:07.775291525-05:00[America/Bogota]", comments = "Generator version: 7.8.0")
public class CasesSummary {

  private String clientId;

  private String correlationId;

  private String summaryText;

  private Integer openCases;

  public CasesSummary() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CasesSummary(String clientId, String correlationId, String summaryText, Integer openCases) {
    this.clientId = clientId;
    this.correlationId = correlationId;
    this.summaryText = summaryText;
    this.openCases = openCases;
  }

  public CasesSummary clientId(String clientId) {
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

  public CasesSummary correlationId(String correlationId) {
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

  public CasesSummary summaryText(String summaryText) {
    this.summaryText = summaryText;
    return this;
  }

  /**
   * Get summaryText
   * @return summaryText
   */
  @NotNull 
  @Schema(name = "summaryText", example = "2 open cases, 1 pending", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("summaryText")
  public String getSummaryText() {
    return summaryText;
  }

  public void setSummaryText(String summaryText) {
    this.summaryText = summaryText;
  }

  public CasesSummary openCases(Integer openCases) {
    this.openCases = openCases;
    return this;
  }

  /**
   * Get openCases
   * minimum: 0
   * @return openCases
   */
  @NotNull @Min(0) 
  @Schema(name = "openCases", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("openCases")
  public Integer getOpenCases() {
    return openCases;
  }

  public void setOpenCases(Integer openCases) {
    this.openCases = openCases;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CasesSummary casesSummary = (CasesSummary) o;
    return Objects.equals(this.clientId, casesSummary.clientId) &&
        Objects.equals(this.correlationId, casesSummary.correlationId) &&
        Objects.equals(this.summaryText, casesSummary.summaryText) &&
        Objects.equals(this.openCases, casesSummary.openCases);
  }

  @Override
  public int hashCode() {
    return Objects.hash(clientId, correlationId, summaryText, openCases);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CasesSummary {\n");
    sb.append("    clientId: ").append(toIndentedString(clientId)).append("\n");
    sb.append("    correlationId: ").append(toIndentedString(correlationId)).append("\n");
    sb.append("    summaryText: ").append(toIndentedString(summaryText)).append("\n");
    sb.append("    openCases: ").append(toIndentedString(openCases)).append("\n");
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

