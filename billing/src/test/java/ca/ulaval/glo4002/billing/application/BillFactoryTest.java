package ca.ulaval.glo4002.billing.application;

import ca.ulaval.glo4002.billing.application.dto.BillDto;
import ca.ulaval.glo4002.billing.builders.dto.BillDtoBuilder;
import ca.ulaval.glo4002.billing.domain.Bill;
import ca.ulaval.glo4002.billing.domain.BillItem;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class BillFactoryTest {

    private List<BillItem> EMPTY_BILL_ITEMS = new ArrayList<>();

    @Test
    public void givenParametersToConstructABill_whenMakingANewBill_thenTheBillIsCreated() {
        BillDto billDto = new BillDtoBuilder().withValidValues().build();

        Bill createdBill = (new BillFactory()).createBill(billDto.clientId, billDto.creationDate, billDto.dueTerm, EMPTY_BILL_ITEMS);

        Assert.assertEquals(createdBill.getClientId(), billDto.clientId);
        Assert.assertEquals(createdBill.getCreationDate(), billDto.creationDate);
        Assert.assertEquals(createdBill.getDueTerm(), billDto.dueTerm);
        Assert.assertEquals(createdBill.getItems(), EMPTY_BILL_ITEMS);
    }
}
