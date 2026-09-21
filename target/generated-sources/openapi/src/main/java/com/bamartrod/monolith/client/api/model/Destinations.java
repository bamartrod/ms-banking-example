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
 * Destinations
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-21T09:36:10.147188854-05:00[America/Bogota]", comments = "Generator version: 7.8.0")
public class Destinations {

  private String clientId;

  private String correlationId;

  private String destinationCode;

  private Boolean active;

  public Destinations() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Destinations(String clientId, String correlationId, String destinationCode, Boolean active) {
    this.clientId = clientId;
    this.correlationId = correlationId;
    this.destinationCode = destinationCode;
    this.active = active;
  }

  public Destinations clientId(String clientId) {
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

  public Destinations correlationId(String correlationId) {
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

  public Destinations destinationCode(String destinationCode) {
    this.destinationCode = destinationCode;
    return this;
  }

  /**
   * Get destinationCode
   * @return destinationCode
   */
  @NotNull 
  @Schema(name = "destinationCode", example = "DEST-001", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("destinationCode")
  public String getDestinationCode() {
    return destinationCode;
  }

  public void setDestinationCode(String destinationCode) {
    this.destinationCode = destinationCode;
  }

  public Destinations active(Boolean active) {
    this.active = active;
    return this;
  }

  /**
   * Get active
   * @return active
   */
  @NotNull 
  @Schema(name = "active", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("active")
  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Destinations destinations = (Destinations) o;
    return Objects.equals(this.clientId, destinations.clientId) &&
        Objects.equals(this.correlationId, destinations.correlationId) &&
        Objects.equals(this.destinationCode, destinations.destinationCode) &&
        Objects.equals(this.active, destinations.active);
  }

  @Override
  public int hashCode() {
    return Objects.hash(clientId, correlationId, destinationCode, active);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Destinations {\n");
    sb.append("    clientId: ").append(toIndentedString(clientId)).append("\n");
    sb.append("    correlationId: ").append(toIndentedString(correlationId)).append("\n");
    sb.append("    destinationCode: ").append(toIndentedString(destinationCode)).append("\n");
    sb.append("    active: ").append(toIndentedString(active)).append("\n");
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

