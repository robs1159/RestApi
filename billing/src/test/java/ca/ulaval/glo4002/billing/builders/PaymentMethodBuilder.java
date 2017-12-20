package ca.ulaval.glo4002.billing.builders;

import ca.ulaval.glo4002.billing.domain.PaymentMethod;
import ca.ulaval.glo4002.billing.domain.Source;

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

    public PaymentMethod build() {
        return new PaymentMethod(this.account, this.source);
    }
}
