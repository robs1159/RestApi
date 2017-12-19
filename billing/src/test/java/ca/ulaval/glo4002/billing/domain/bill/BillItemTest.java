package ca.ulaval.glo4002.billing.domain.bill;

import ca.ulaval.glo4002.billing.test.utils.builders.builders.BillItemBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class BillItemTest {

    @Test
    public void givenNewItem_whenCreatedWithValidPriceAndQuantity_thenShouldReturnAValidPriceOfItem() {
        BillItem billItem = new BillItemBuilder().withValidValues().build();
        BigDecimal validPriceOfItem = billItem.getPrice().multiply(BigDecimal.valueOf(billItem.getQuantity()));

        Assert.assertTrue(billItem.calculatePriceItem().equals(validPriceOfItem));
    }
}