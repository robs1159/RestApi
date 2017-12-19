package ca.ulaval.glo4002.payment.application.assembler;

import ca.ulaval.glo4002.payment.application.dto.PaymentMethodDto;
import ca.ulaval.glo4002.payment.domain.payment.PaymentMethod;

public class PaymentMethodAssembler {

    public static PaymentMethod createPaymentMethodFromDto(PaymentMethodDto paymentMethodDto){
        return new PaymentMethod(paymentMethodDto.account, paymentMethodDto.source);
    }
}
