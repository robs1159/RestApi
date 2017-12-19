package ca.ulaval.glo4002.billing.application.assembler;

import ca.ulaval.glo4002.billing.application.dto.AcceptedBillToReturnDto;
import ca.ulaval.glo4002.billing.application.dto.BillDto;
import ca.ulaval.glo4002.billing.application.dto.BillToReturnDto;
import ca.ulaval.glo4002.billing.domain.bill.Bill;
import ca.ulaval.glo4002.billing.domain.bill.BillId;

public class BillAssembler {

    private static final String BILLS_URI = "/bills/";

    public BillToReturnDto toDto(Bill bill) {
        BillToReturnDto billToReturnDto = new BillToReturnDto();
        BillId billId = bill.getId();

        billToReturnDto.id = billId.getBillId();
        billToReturnDto.total = bill.calculateTotal();
        billToReturnDto.dueTerm = bill.getDueTerm();
        billToReturnDto.url = this.buildBillURI(billId);

        return billToReturnDto;
    }

    public static Bill createBillFromDto(BillDto billDto) {
        return new Bill(billDto.clientId, billDto.creationDate, billDto.dueTerm, BillItemAssembler.createBillItemFromDto(billDto.items));
    }

    public AcceptedBillToReturnDto toAcceptedDtoReturn(Bill bill) {
        AcceptedBillToReturnDto acceptedBillToReturnDto = new AcceptedBillToReturnDto();
        BillId billId = bill.getId();

        acceptedBillToReturnDto.id = billId.getBillId();
        acceptedBillToReturnDto.dueTerm = bill.getDueTerm();
        acceptedBillToReturnDto.url = this.buildBillURI(billId);
        acceptedBillToReturnDto.effectiveDate = bill.getEffectiveDate();
        acceptedBillToReturnDto.expectedPayment = bill.getExpectedPayment();

        return acceptedBillToReturnDto;
    }


    private String buildBillURI(BillId billId) {
        return BILLS_URI + billId.getBillId();
    }
}
