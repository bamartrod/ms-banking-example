package com.bamartrod.monolith.interfaces.marketing.api.model;

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
 * Prospect
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-21T09:36:09.027775183-05:00[America/Bogota]", comments = "Generator version: 7.8.0")
public class Prospect {

  private String clientId;

  private String correlationId;

  private String prospectId;

  private String stage;

  public Prospect() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Prospect(String clientId, String correlationId, String prospectId, String stage) {
    this.clientId = clientId;
    this.correlationId = correlationId;
    this.prospectId = prospectId;
    this.stage = stage;
  }

  public Prospect clientId(String clientId) {
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

  public Prospect correlationId(String correlationId) {
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

  public Prospect prospectId(String prospectId) {
    this.prospectId = prospectId;
    return this;
  }

  /**
   * Get prospectId
   * @return prospectId
   */
  @NotNull 
  @Schema(name = "prospectId", example = "PROS-123", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("prospectId")
  public String getProspectId() {
    return prospectId;
  }

  public void setProspectId(String prospectId) {
    this.prospectId = prospectId;
  }

  public Prospect stage(String stage) {
    this.stage = stage;
    return this;
  }

  /**
   * Get stage
   * @return stage
   */
  @NotNull 
  @Schema(name = "stage", example = "STAGE_1", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("stage")
  public String getStage() {
    return stage;
  }

  public void setStage(String stage) {
    this.stage = stage;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Prospect prospect = (Prospect) o;
    return Objects.equals(this.clientId, prospect.clientId) &&
        Objects.equals(this.correlationId, prospect.correlationId) &&
        Objects.equals(this.prospectId, prospect.prospectId) &&
        Objects.equals(this.stage, prospect.stage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(clientId, correlationId, prospectId, stage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Prospect {\n");
    sb.append("    clientId: ").append(toIndentedString(clientId)).append("\n");
    sb.append("    correlationId: ").append(toIndentedString(correlationId)).append("\n");
    sb.append("    prospectId: ").append(toIndentedString(prospectId)).append("\n");
    sb.append("    stage: ").append(toIndentedString(stage)).append("\n");
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

