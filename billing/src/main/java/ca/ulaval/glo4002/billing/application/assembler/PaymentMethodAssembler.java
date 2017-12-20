package ca.ulaval.glo4002.billing.application.assembler;

import ca.ulaval.glo4002.billing.application.dto.PaymentMethodDto;
import ca.ulaval.glo4002.billing.domain.PaymentMethod;

public class PaymentMethodAssembler {

    public PaymentMethod createPaymentMethodFromDto(PaymentMethodDto paymentMethodDto) {
        return new PaymentMethod(paymentMethodDto.account, paymentMethodDto.source);
    }
}
