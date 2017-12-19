package ca.ulaval.glo4002.payment.application.dto;

import ca.ulaval.glo4002.crmInterface.domain.ClientId;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentDto {

    @JsonProperty
    public ClientId clientId;

    @JsonProperty
    public float amount;

    @JsonProperty
    public PaymentMethodDto paymentMethod;
}
