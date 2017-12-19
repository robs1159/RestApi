package ca.ulaval.glo4002.payment.test.utilis.builders.builders.dto;

import ca.ulaval.glo4002.crmInterface.domain.ClientId;
import ca.ulaval.glo4002.payment.application.dto.PaymentDto;
import ca.ulaval.glo4002.payment.application.dto.PaymentMethodDto;

public class PaymentDtoBuilder {

    private static final ClientId VALID_CLIENT_ID = new ClientId(1);
    private static final float AMOUNT = 10.9f;

    private ClientId clientId;
    private float amount;
    private PaymentMethodDto paymentMethodDto;

    public PaymentDtoBuilder withValidValues() {
        this.clientId = VALID_CLIENT_ID;
        this.amount = AMOUNT;
        this.paymentMethodDto = new PaymentMethodDtoBuilder().withValidValues().build();

        return this;
    }

    public PaymentDto build() {
        PaymentDto buildPaymentDto = new PaymentDto();

        buildPaymentDto.clientId = this.clientId;
        buildPaymentDto.amount = this.amount;
        buildPaymentDto.paymentMethod = this.paymentMethodDto;

        return buildPaymentDto;
    }
}
