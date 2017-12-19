package ca.ulaval.glo4002.payment.application;

import ca.ulaval.glo4002.crmInterface.domain.ClientId;
import ca.ulaval.glo4002.payment.domain.payment.Payment;
import ca.ulaval.glo4002.payment.domain.payment.PaymentMethod;
import ca.ulaval.glo4002.payment.domain.payment.Source;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PaymentFactoryTest {

    private static final int DELTA_FLOAT_TEST = 0;

    private static final float AMOUNT = 10.9f;
    private static final String ACCOUNT = "1111-111-111";
    private static final long VALID_CLIENT_ID = 1;

    private ClientId clientId;

    @Before
    public void setup() {
        clientId = new ClientId(VALID_CLIENT_ID);
    }

    @Test
    public void givenParametersToConstructPayment_whenMakingANewPayment_thenPaymentIsCreated() {
        PaymentMethod paymentMethod = new PaymentMethod(ACCOUNT, Source.CHECK);
        Payment payment = PaymentFactory.createPayment(clientId, AMOUNT, paymentMethod);

        Assert.assertEquals(payment.getClientId(), clientId);
        Assert.assertEquals(payment.getAmount().floatValue(), AMOUNT, DELTA_FLOAT_TEST);
        Assert.assertEquals(payment.getPaymentMethod().getAccount(), ACCOUNT);
        Assert.assertEquals(payment.getPaymentMethod().getSource(), Source.CHECK);
    }
}
