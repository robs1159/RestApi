package ca.ulaval.glo4002.billing.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HeartbeatDto {
    @JsonProperty
    public long date;

    @JsonProperty
    public String token;
}
