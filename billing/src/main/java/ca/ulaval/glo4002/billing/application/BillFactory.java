package ca.ulaval.glo4002.billing.application;

import ca.ulaval.glo4002.billing.domain.bill.Bill;
import ca.ulaval.glo4002.billing.domain.bill.BillItem;
import ca.ulaval.glo4002.crmInterface.domain.ClientId;
import ca.ulaval.glo4002.crmInterface.domain.DueTerm;

import java.util.List;

public class BillFactory {

    public static Bill createBill(ClientId clientId, String creationDate, DueTerm dueTerm, List<BillItem> billItems) {
        return new Bill(clientId, creationDate, dueTerm, billItems);
    }
}
