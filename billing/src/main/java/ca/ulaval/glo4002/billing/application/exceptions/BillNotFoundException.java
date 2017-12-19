package ca.ulaval.glo4002.billing.application.exceptions;

import ca.ulaval.glo4002.billing.domain.bill.BillId;

public class BillNotFoundException extends Exception {

    private final BillId billId;

    public BillNotFoundException(BillId billId) {
        this.billId = billId;
    }

    public BillId getBillId() {
        return billId;
    }

}
