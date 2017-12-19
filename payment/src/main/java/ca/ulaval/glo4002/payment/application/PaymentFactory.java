package ca.ulaval.glo4002.payment.application;

import ca.ulaval.glo4002.crmInterface.domain.ClientId;
import ca.ulaval.glo4002.payment.domain.payment.Payment;
import ca.ulaval.glo4002.payment.domain.payment.PaymentMethod;

public class PaymentFactory {

    public static Payment createPayment(ClientId clientId, float amount, PaymentMethod paymentMethod) {
        return new Payment(clientId, amount, paymentMethod);
    }
}
