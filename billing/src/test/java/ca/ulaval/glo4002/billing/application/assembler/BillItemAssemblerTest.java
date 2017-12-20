package ca.ulaval.glo4002.billing.application.assembler;

import ca.ulaval.glo4002.billing.application.dto.BillItemDto;
import ca.ulaval.glo4002.billing.builders.BillItemBuilder;
import ca.ulaval.glo4002.billing.builders.dto.BillItemDtoBuilder;
import ca.ulaval.glo4002.billing.domain.BillItem;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

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
        List<BillItem> validBillItems = (new BillItemAssembler()).createBillItemFromDto(validBillItemsDto);

        BillItem validBillItem = new BillItemBuilder().withNote(validBillItemDto.note).
                withProductId(validBillItemDto.productId).
                withPrice(validBillItemDto.price).
                withQuantity(validBillItemDto.quantity).build();

        assertTrue(validBillItem.getPrice() == validBillItems.get(0).getPrice());
        assertTrue(validBillItem.getQuantity() == validBillItems.get(0).getQuantity());
    }
}
