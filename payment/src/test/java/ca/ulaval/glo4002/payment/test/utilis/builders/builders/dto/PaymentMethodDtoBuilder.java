package ca.ulaval.glo4002.payment.test.utilis.builders.builders.dto;

import ca.ulaval.glo4002.payment.application.dto.PaymentMethodDto;
import ca.ulaval.glo4002.payment.domain.payment.Source;

public class PaymentMethodDtoBuilder {

    private static final String ACCOUNT = "XXXX-XXX-XXX";
    private static final Source SOURCE = Source.CHECK;

    private String account;
    private Source source;

    public PaymentMethodDtoBuilder withValidValues() {
        this.account = ACCOUNT;
        this.source = SOURCE;

        return this;
    }

    public PaymentMethodDto build(){
        PaymentMethodDto paymentMethodDto = new PaymentMethodDto();

        paymentMethodDto.account = this.account;
        paymentMethodDto.source = source;

        return paymentMethodDto;
    }
}
