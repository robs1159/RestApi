package ca.ulaval.glo4002.billing.application.dto;

import ca.ulaval.glo4002.billing.domain.Source;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentMethodDto {

    @JsonProperty
    public String account;

    @JsonProperty
    public Source source;
}
