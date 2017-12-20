package ca.ulaval.glo4002.billing.application.assembler;

import ca.ulaval.glo4002.billing.application.dto.PaymentDto;
import ca.ulaval.glo4002.billing.application.dto.PaymentToReturnDto;
import ca.ulaval.glo4002.billing.domain.Payment;
import ca.ulaval.glo4002.billing.domain.PaymentId;

public class PaymentAssembler {

    private static final String PAYMENTS_URI = "/payments/";

    public PaymentToReturnDto toDto(Payment payment) {
        PaymentToReturnDto paymentToReturnDto = new PaymentToReturnDto();

        paymentToReturnDto.id = payment.getPaymentId().getUniqueId();
        paymentToReturnDto.url = buildPaymentURI(payment.getPaymentId());

        return paymentToReturnDto;
    }

    public Payment createPaymentFromDto(PaymentDto paymentDto) {
        PaymentMethodAssembler paymentMethodAssembler = new PaymentMethodAssembler();
        return new Payment(paymentDto.clientId, paymentDto.amount, paymentMethodAssembler.createPaymentMethodFromDto(paymentDto.paymentMethod));
    }

    public String buildPaymentURI(PaymentId PaymentId) {
        return PAYMENTS_URI + PaymentId.getUniqueId();
    }
}
