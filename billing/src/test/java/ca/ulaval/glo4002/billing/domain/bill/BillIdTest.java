package ca.ulaval.glo4002.billing.domain.bill;

import org.junit.Assert;
import org.junit.Test;

public class BillIdTest {

    @Test
    public void givenNoBillId_whenNeedNewUniqueBillId_thenGetValidUniqueBillId() {
        BillId billId = new BillId();
        Assert.assertTrue(billId.getBillId() >= 0);
    }
}
