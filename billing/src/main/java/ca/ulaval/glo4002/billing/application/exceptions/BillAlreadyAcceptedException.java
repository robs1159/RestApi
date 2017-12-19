package ca.ulaval.glo4002.billing.application.exceptions;

import ca.ulaval.glo4002.billing.domain.bill.BillId;

public class BillAlreadyAcceptedException extends RuntimeException {

    private final BillId billId;

    public BillAlreadyAcceptedException(BillId billId) {
        this.billId = billId;
    }

    public BillId getBillId() {
        return billId;
    }
}
