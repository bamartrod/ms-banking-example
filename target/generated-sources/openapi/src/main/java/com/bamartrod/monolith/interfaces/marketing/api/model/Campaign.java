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
 * Campaign
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-21T09:36:09.027775183-05:00[America/Bogota]", comments = "Generator version: 7.8.0")
public class Campaign {

  private String clientId;

  private String correlationId;

  private String campaignCode;

  private String description;

  public Campaign() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Campaign(String clientId, String correlationId, String campaignCode, String description) {
    this.clientId = clientId;
    this.correlationId = correlationId;
    this.campaignCode = campaignCode;
    this.description = description;
  }

  public Campaign clientId(String clientId) {
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

  public Campaign correlationId(String correlationId) {
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

  public Campaign campaignCode(String campaignCode) {
    this.campaignCode = campaignCode;
    return this;
  }

  /**
   * Get campaignCode
   * @return campaignCode
   */
  @NotNull 
  @Schema(name = "campaignCode", example = "CAMP-001", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("campaignCode")
  public String getCampaignCode() {
    return campaignCode;
  }

  public void setCampaignCode(String campaignCode) {
    this.campaignCode = campaignCode;
  }

  public Campaign description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
   */
  @NotNull 
  @Schema(name = "description", example = "Summer 2026 premium campaign", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Campaign campaign = (Campaign) o;
    return Objects.equals(this.clientId, campaign.clientId) &&
        Objects.equals(this.correlationId, campaign.correlationId) &&
        Objects.equals(this.campaignCode, campaign.campaignCode) &&
        Objects.equals(this.description, campaign.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(clientId, correlationId, campaignCode, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Campaign {\n");
    sb.append("    clientId: ").append(toIndentedString(clientId)).append("\n");
    sb.append("    correlationId: ").append(toIndentedString(correlationId)).append("\n");
    sb.append("    campaignCode: ").append(toIndentedString(campaignCode)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
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

