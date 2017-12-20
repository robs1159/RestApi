package ca.ulaval.glo4002.billing.builders;

import ca.ulaval.glo4002.billing.domain.ClientId;
import ca.ulaval.glo4002.billing.domain.Payment;
import ca.ulaval.glo4002.billing.domain.PaymentMethod;

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

    //TODO:Devrait être mocker
    public Payment build() {
        return new Payment(this.clientId, this.amount, this.paymentMethod);
    }
}
