package ca.ulaval.glo4002.billing.builders.dto;

import ca.ulaval.glo4002.billing.application.dto.BillDto;
import ca.ulaval.glo4002.billing.application.dto.BillItemDto;
import ca.ulaval.glo4002.billing.domain.ClientId;
import ca.ulaval.glo4002.billing.domain.DueTerm;

import java.util.ArrayList;
import java.util.List;

public class BillDtoBuilder {

    private static final ClientId VALID_CLIENT_ID = new ClientId(1);
    private static final String VALID_CREATION_DATE = "2011-02-02";
    private static final DueTerm VALID_DUE_TERM = DueTerm.IMMEDIATE;

    private ClientId clientId;
    private String creationDate;
    private DueTerm dueTerm;
    private List<BillItemDto> items;

    public BillDtoBuilder withItems(List<BillItemDto> items) {
        this.items = items;
        return this;
    }

    public BillDtoBuilder withValidValues() {
        this.clientId = VALID_CLIENT_ID;
        this.creationDate = VALID_CREATION_DATE;
        this.dueTerm = VALID_DUE_TERM;

        BillItemDto validBillItem = new BillItemDtoBuilder().withValidValues().build();
        List<BillItemDto> items = new ArrayList<>();
        items.add(validBillItem);

        this.items = items;

        return this;
    }

    public BillDto build() {
        BillDto buildBillDto = new BillDto();

        buildBillDto.clientId = this.clientId;
        buildBillDto.creationDate = this.creationDate;
        buildBillDto.dueTerm = this.dueTerm;
        buildBillDto.items = (ArrayList<BillItemDto>) this.items;

        return buildBillDto;
    }
}
