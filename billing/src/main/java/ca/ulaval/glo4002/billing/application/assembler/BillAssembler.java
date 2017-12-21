package ca.ulaval.glo4002.billing.application.assembler;

import ca.ulaval.glo4002.billing.application.dto.AcceptedBillToReturnDto;
import ca.ulaval.glo4002.billing.application.dto.BillDto;
import ca.ulaval.glo4002.billing.domain.Bill;
import ca.ulaval.glo4002.billing.domain.BillId;

public class BillAssembler {

    private static final String BILLS_URI = "/bills/";

    public BillDto toDto(Bill bill) {
        BillDto billDto = new BillDto();

        billDto.id = bill.getId().getId();
        billDto.total = bill.calculateTotal();
        billDto.dueTerm = bill.getDueTerm();
        billDto.url = this.buildBillURI(bill.getId());

        return billDto;
    }

    public Bill createBillFromDto(BillDto billDto) {
        return new Bill(billDto.clientId, billDto.creationDate, billDto.dueTerm, (new BillItemAssembler()).createBillItemFromDto(billDto.items));
    }

    public AcceptedBillToReturnDto toAcceptedDto(Bill bill) {
        AcceptedBillToReturnDto acceptedBillToReturnDto = new AcceptedBillToReturnDto();

        acceptedBillToReturnDto.id = bill.getId().getId();
        acceptedBillToReturnDto.dueTerm = bill.getDueTerm();
        acceptedBillToReturnDto.url = this.buildBillURI(bill.getId());
        acceptedBillToReturnDto.expectedPayment = bill.getExpectedPayment().toInstant();
        acceptedBillToReturnDto.effectiveDate = bill.getEffectiveDate().toInstant();

        return acceptedBillToReturnDto;
    }

    private String buildBillURI(BillId billId) {
        return BILLS_URI + billId.getId();
    }
}
