package ca.ulaval.glo4002.billing.application.repositories;

import ca.ulaval.glo4002.billing.domain.BillId;

public class BillNotFoundException extends Exception {

    private final BillId billId;

    public BillNotFoundException(BillId billId) {
        this.billId = billId;
    }

    public BillId getBillId() {
        return billId;
    }

}
