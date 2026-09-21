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
 * Marketing
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-21T09:36:09.027775183-05:00[America/Bogota]", comments = "Generator version: 7.8.0")
public class Marketing {

  private String clientId;

  private String correlationId;

  private String campaignId;

  private String segment;

  public Marketing() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Marketing(String clientId, String correlationId, String campaignId, String segment) {
    this.clientId = clientId;
    this.correlationId = correlationId;
    this.campaignId = campaignId;
    this.segment = segment;
  }

  public Marketing clientId(String clientId) {
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

  public Marketing correlationId(String correlationId) {
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

  public Marketing campaignId(String campaignId) {
    this.campaignId = campaignId;
    return this;
  }

  /**
   * Get campaignId
   * @return campaignId
   */
  @NotNull 
  @Schema(name = "campaignId", example = "CAMP-001", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("campaignId")
  public String getCampaignId() {
    return campaignId;
  }

  public void setCampaignId(String campaignId) {
    this.campaignId = campaignId;
  }

  public Marketing segment(String segment) {
    this.segment = segment;
    return this;
  }

  /**
   * Get segment
   * @return segment
   */
  @NotNull 
  @Schema(name = "segment", example = "PREMIUM", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("segment")
  public String getSegment() {
    return segment;
  }

  public void setSegment(String segment) {
    this.segment = segment;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Marketing marketing = (Marketing) o;
    return Objects.equals(this.clientId, marketing.clientId) &&
        Objects.equals(this.correlationId, marketing.correlationId) &&
        Objects.equals(this.campaignId, marketing.campaignId) &&
        Objects.equals(this.segment, marketing.segment);
  }

  @Override
  public int hashCode() {
    return Objects.hash(clientId, correlationId, campaignId, segment);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Marketing {\n");
    sb.append("    clientId: ").append(toIndentedString(clientId)).append("\n");
    sb.append("    correlationId: ").append(toIndentedString(correlationId)).append("\n");
    sb.append("    campaignId: ").append(toIndentedString(campaignId)).append("\n");
    sb.append("    segment: ").append(toIndentedString(segment)).append("\n");
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

