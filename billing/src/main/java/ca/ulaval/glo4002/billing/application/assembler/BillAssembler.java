package ca.ulaval.glo4002.billing.application.assembler;

import ca.ulaval.glo4002.billing.application.dto.AcceptedBillToReturnDto;
import ca.ulaval.glo4002.billing.application.dto.BillDto;
import ca.ulaval.glo4002.billing.domain.Bill;
import ca.ulaval.glo4002.billing.domain.BillId;

public class BillAssembler {

    private static final String BILLS_URI = "/bills/";

    public BillDto toDto(Bill bill) {
        BillDto billDto = new BillDto();
        BillId billId = bill.getId();

        billDto.id = billId.getId();
        billDto.total = bill.calculateTotal();
        billDto.dueTerm = bill.getDueTerm();
        billDto.url = this.buildBillURI(billId);
        billDto.clientId = bill.getClientId();
        billDto.creationDate = bill.getCreationDate();

        return billDto;
    }

    public Bill createBillFromDto(BillDto billDto) {
        return new Bill(billDto.clientId, billDto.creationDate, billDto.dueTerm, (new BillItemAssembler()).createBillItemFromDto(billDto.items));
    }

    public AcceptedBillToReturnDto toAcceptedDto(Bill bill) {
        AcceptedBillToReturnDto acceptedBillToReturnDto = new AcceptedBillToReturnDto();
        BillId billId = bill.getId();

        acceptedBillToReturnDto.id = billId.getId();
        acceptedBillToReturnDto.dueTerm = bill.getDueTerm();
        acceptedBillToReturnDto.url = this.buildBillURI(billId);
        acceptedBillToReturnDto.effectiveDate = bill.getEffectiveDate();
        acceptedBillToReturnDto.expectedPayment = bill.getExpectedPayment();

        return acceptedBillToReturnDto;
    }

    private String buildBillURI(BillId billId) {
        return BILLS_URI + billId.getId();
    }
}
