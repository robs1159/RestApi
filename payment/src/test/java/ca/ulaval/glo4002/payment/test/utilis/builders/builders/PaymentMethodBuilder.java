package ca.ulaval.glo4002.payment.test.utilis.builders.builders;

import ca.ulaval.glo4002.payment.domain.payment.PaymentMethod;
import ca.ulaval.glo4002.payment.domain.payment.Source;

public class PaymentMethodBuilder {

    private static final String ACCOUNT = "XXXX-XXX-XXX";
    private static final Source SOURCE = Source.CHECK;

    private String account;
    private Source source;

    public PaymentMethodBuilder withValidValues() {
        this.account = ACCOUNT;
        this.source = SOURCE;

        return this;
    }

    public PaymentMethod build(){
        return new PaymentMethod(this.account, this.source);
    }
}
