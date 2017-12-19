package ca.ulaval.glo4002.payment.application.dto;

import ca.ulaval.glo4002.payment.domain.payment.Source;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentMethodDto {

    @JsonProperty
    public String account;

    @JsonProperty
    public Source source;
}
