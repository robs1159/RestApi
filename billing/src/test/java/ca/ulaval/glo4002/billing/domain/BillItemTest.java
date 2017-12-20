package ca.ulaval.glo4002.billing.domain;

import ca.ulaval.glo4002.billing.builders.BillItemBuilder;
import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertEquals;

public class BillItemTest {

    @Test
    public void givenNewItem_whenCreatedWithValidPriceAndQuantity_thenShouldReturnAValidPriceOfItem() {
        BillItem billItem = new BillItemBuilder().withValidValues().build();
        BigDecimal validPriceOfItem = billItem.getPrice().multiply(BigDecimal.valueOf(billItem.getQuantity()));

        assertEquals(billItem.calculatePrice(), validPriceOfItem);
    }
}