package ca.ulaval.glo4002.payment.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentToReturnDto {

    @JsonProperty
    public long id;

    @JsonProperty
    public String url;
}
