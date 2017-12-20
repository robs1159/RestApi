package ca.ulaval.glo4002.billing.domain;

import org.junit.Assert;
import org.junit.Test;

public class PaymentIdTest {

    @Test
    public void givenNoPaymentId_whenNeedNewUniquePaymentId_thenCheckPersistentPaymentId() {
        PaymentId paymentId = new PaymentId();

        Assert.assertEquals(paymentId.getUniqueId(), paymentId.getUniqueId());
    }

    @Test
    public void givenNoPaymentId_whenNeedNewUniquePaymentId_thenGetValidUniquePaymentId() {
        PaymentId paymentId1 = new PaymentId();
        PaymentId paymentId2 = new PaymentId();

        Assert.assertNotEquals(paymentId1.getUniqueId(), paymentId2.getUniqueId());
    }
}
