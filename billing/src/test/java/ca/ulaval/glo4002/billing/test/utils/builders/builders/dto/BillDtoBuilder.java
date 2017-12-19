package ca.ulaval.glo4002.billing.test.utils.builders.builders.dto;

import ca.ulaval.glo4002.billing.application.dto.BillDto;
import ca.ulaval.glo4002.billing.application.dto.BillItemDto;
import ca.ulaval.glo4002.crmInterface.domain.ClientId;
import ca.ulaval.glo4002.crmInterface.domain.DueTerm;

import java.math.BigDecimal;
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

    public BillDtoBuilder withClientId(ClientId clientId) {
        this.clientId = clientId;
        return this;
    }

    public BillDtoBuilder withCreationDate(String creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public BillDtoBuilder withDueTerm(DueTerm dueTerm) {
        this.dueTerm = dueTerm;
        return this;
    }

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

    //todo a supprimer.  Seulement pour démontrer comment ça fonctionne.
    public static void main(String args[]) {

        List<BillItemDto> lst = new ArrayList<>();

        lst.add(new BillItemDtoBuilder().withValidValues().build());
        lst.add(new BillItemDtoBuilder().withValidValues().build());
        lst.add(new BillItemDtoBuilder().withValidValues().withPrice(new BigDecimal(3.69)).build());

        BillDto billDto = new BillDtoBuilder().withValidValues().withItems(lst).build();
        System.out.println(billDto.items.get(2).price);

        billDto = new BillDtoBuilder().withValidValues().build();
        System.out.println(billDto.items.get(0).price);

    }
}
