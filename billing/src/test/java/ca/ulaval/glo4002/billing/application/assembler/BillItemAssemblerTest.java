package ca.ulaval.glo4002.billing.application.assembler;

import ca.ulaval.glo4002.billing.application.dto.BillItemDto;
import ca.ulaval.glo4002.billing.domain.bill.BillItem;
import ca.ulaval.glo4002.billing.test.utils.builders.builders.BillItemBuilder;
import ca.ulaval.glo4002.billing.test.utils.builders.builders.dto.BillItemDtoBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import java.util.ArrayList;
import java.util.List;

public class BillItemAssemblerTest {

    private List<BillItemDto> validBillItemsDto;
    private BillItemDto validBillItemDto;

    @Before
    public void setupBillItemDTO() {
        validBillItemsDto = new ArrayList<>();
        validBillItemDto = new BillItemDtoBuilder().withValidValues().build();
        validBillItemsDto.add(validBillItemDto);
    }

    @Test
    public void givenValidBillItemDTO_whenCreateNewBillItem_thenGetValidBillItem() {
        List<BillItem> validBillItems = BillItemAssembler.createBillItemFromDto(validBillItemsDto);

        BillItem validBillItem = new BillItemBuilder().withNote(validBillItemDto.note).
                withProductId(validBillItemDto.productId).
                withPrice(validBillItemDto.price).
                withQuantity(validBillItemDto.quantity).build();

        Assert.assertThat(validBillItem, new ReflectionEquals(validBillItems.get(0)));
    }
}
