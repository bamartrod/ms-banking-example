package com.bamartrod.monolith.interfaces.clientenrichment.api.model;

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
 * BelongsDocument
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-09-21T09:36:09.613785717-05:00[America/Bogota]", comments = "Generator version: 7.8.0")
public class BelongsDocument {

  private String clientId;

  private String correlationId;

  private String documentType;

  private String belongsFlag;

  public BelongsDocument() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public BelongsDocument(String clientId, String correlationId, String documentType, String belongsFlag) {
    this.clientId = clientId;
    this.correlationId = correlationId;
    this.documentType = documentType;
    this.belongsFlag = belongsFlag;
  }

  public BelongsDocument clientId(String clientId) {
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

  public BelongsDocument correlationId(String correlationId) {
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

  public BelongsDocument documentType(String documentType) {
    this.documentType = documentType;
    return this;
  }

  /**
   * Get documentType
   * @return documentType
   */
  @NotNull 
  @Schema(name = "documentType", example = "CC", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("documentType")
  public String getDocumentType() {
    return documentType;
  }

  public void setDocumentType(String documentType) {
    this.documentType = documentType;
  }

  public BelongsDocument belongsFlag(String belongsFlag) {
    this.belongsFlag = belongsFlag;
    return this;
  }

  /**
   * Get belongsFlag
   * @return belongsFlag
   */
  @NotNull 
  @Schema(name = "belongsFlag", example = "YES", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("belongsFlag")
  public String getBelongsFlag() {
    return belongsFlag;
  }

  public void setBelongsFlag(String belongsFlag) {
    this.belongsFlag = belongsFlag;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BelongsDocument belongsDocument = (BelongsDocument) o;
    return Objects.equals(this.clientId, belongsDocument.clientId) &&
        Objects.equals(this.correlationId, belongsDocument.correlationId) &&
        Objects.equals(this.documentType, belongsDocument.documentType) &&
        Objects.equals(this.belongsFlag, belongsDocument.belongsFlag);
  }

  @Override
  public int hashCode() {
    return Objects.hash(clientId, correlationId, documentType, belongsFlag);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BelongsDocument {\n");
    sb.append("    clientId: ").append(toIndentedString(clientId)).append("\n");
    sb.append("    correlationId: ").append(toIndentedString(correlationId)).append("\n");
    sb.append("    documentType: ").append(toIndentedString(documentType)).append("\n");
    sb.append("    belongsFlag: ").append(toIndentedString(belongsFlag)).append("\n");
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

