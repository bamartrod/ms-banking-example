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
 * CasesOrq
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-21T09:36:07.775291525-05:00[America/Bogota]", comments = "Generator version: 7.8.0")
public class CasesOrq {

  private String clientId;

  private String correlationId;

  private String orchestrationId;

  private String currentStep;

  public CasesOrq() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CasesOrq(String clientId, String correlationId, String orchestrationId, String currentStep) {
    this.clientId = clientId;
    this.correlationId = correlationId;
    this.orchestrationId = orchestrationId;
    this.currentStep = currentStep;
  }

  public CasesOrq clientId(String clientId) {
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

  public CasesOrq correlationId(String correlationId) {
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

  public CasesOrq orchestrationId(String orchestrationId) {
    this.orchestrationId = orchestrationId;
    return this;
  }

  /**
   * Get orchestrationId
   * @return orchestrationId
   */
  @NotNull 
  @Schema(name = "orchestrationId", example = "ORQ-001", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("orchestrationId")
  public String getOrchestrationId() {
    return orchestrationId;
  }

  public void setOrchestrationId(String orchestrationId) {
    this.orchestrationId = orchestrationId;
  }

  public CasesOrq currentStep(String currentStep) {
    this.currentStep = currentStep;
    return this;
  }

  /**
   * Get currentStep
   * @return currentStep
   */
  @NotNull 
  @Schema(name = "currentStep", example = "STEP_1", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("currentStep")
  public String getCurrentStep() {
    return currentStep;
  }

  public void setCurrentStep(String currentStep) {
    this.currentStep = currentStep;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CasesOrq casesOrq = (CasesOrq) o;
    return Objects.equals(this.clientId, casesOrq.clientId) &&
        Objects.equals(this.correlationId, casesOrq.correlationId) &&
        Objects.equals(this.orchestrationId, casesOrq.orchestrationId) &&
        Objects.equals(this.currentStep, casesOrq.currentStep);
  }

  @Override
  public int hashCode() {
    return Objects.hash(clientId, correlationId, orchestrationId, currentStep);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CasesOrq {\n");
    sb.append("    clientId: ").append(toIndentedString(clientId)).append("\n");
    sb.append("    correlationId: ").append(toIndentedString(correlationId)).append("\n");
    sb.append("    orchestrationId: ").append(toIndentedString(orchestrationId)).append("\n");
    sb.append("    currentStep: ").append(toIndentedString(currentStep)).append("\n");
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

