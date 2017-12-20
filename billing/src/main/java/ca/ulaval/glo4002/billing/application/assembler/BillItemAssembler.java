package ca.ulaval.glo4002.billing.application.assembler;

import ca.ulaval.glo4002.billing.application.dto.BillItemDto;
import ca.ulaval.glo4002.billing.domain.BillItem;

import java.util.ArrayList;
import java.util.List;

public class BillItemAssembler {

    public List<BillItem> createBillItemFromDto(List<BillItemDto> billItemsDto) {
        List<BillItem> billItems = new ArrayList<>();

        for (BillItemDto billItemDto : billItemsDto) {
            billItems.add(new BillItem(billItemDto.price, billItemDto.note, billItemDto.productId, billItemDto.quantity));
        }

        return billItems;
    }
}
