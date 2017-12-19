package ca.ulaval.glo4002.payment.test.utilis.builders.builders;

import ca.ulaval.glo4002.crmInterface.domain.ClientId;
import ca.ulaval.glo4002.payment.domain.payment.Payment;
import ca.ulaval.glo4002.payment.domain.payment.PaymentMethod;

public class PaymentBuilder {

    private static final ClientId VALID_CLIENT_ID = new ClientId(1);
    private static final float AMOUNT = 10.9f;

    private ClientId clientId;
    private float amount;
    private PaymentMethod paymentMethod;

    public PaymentBuilder withValidValues() {
        this.clientId = VALID_CLIENT_ID;
        this.amount = AMOUNT;
        this.paymentMethod = new PaymentMethodBuilder().withValidValues().build();

        return this;
    }

    public Payment build() {
        return new Payment(this.clientId, this.amount, this.paymentMethod);
    }
}
