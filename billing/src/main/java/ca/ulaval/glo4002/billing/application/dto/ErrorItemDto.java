package ca.ulaval.glo4002.billing.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorItemDto {

    @JsonProperty
    public String error;

    @JsonProperty
    public String description;

    @JsonProperty
    public String entity;
}
