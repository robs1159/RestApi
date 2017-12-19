package ca.ulaval.glo4002.payment.application.assembler;

import ca.ulaval.glo4002.payment.application.dto.PaymentDto;
import ca.ulaval.glo4002.payment.application.dto.PaymentToReturnDto;
import ca.ulaval.glo4002.payment.domain.payment.Payment;
import ca.ulaval.glo4002.payment.domain.payment.PaymentId;

public class PaymentAssembler {

    private static final String PAYMENTS_URI = "/payments/";

    public static PaymentToReturnDto toDto(Payment payment){
        PaymentToReturnDto paymentToReturnDto = new PaymentToReturnDto();

        paymentToReturnDto.id = payment.getPaymentId().getUniqueId();
        paymentToReturnDto.url = buildPaymentURI(payment.getPaymentId());

        return paymentToReturnDto;
    }

    public static Payment createPaymentFromDto(PaymentDto paymentDto){
        return new Payment(paymentDto.clientId, paymentDto.amount, PaymentMethodAssembler.createPaymentMethodFromDto(paymentDto.paymentMethod));
    }

    private static String buildPaymentURI(PaymentId PaymentId) {
        return PAYMENTS_URI + PaymentId.getUniqueId();
    }
}
