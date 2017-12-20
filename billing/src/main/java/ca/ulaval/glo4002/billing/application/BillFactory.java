package ca.ulaval.glo4002.billing.application;

import ca.ulaval.glo4002.billing.domain.Bill;
import ca.ulaval.glo4002.billing.domain.BillItem;
import ca.ulaval.glo4002.billing.domain.ClientId;
import ca.ulaval.glo4002.billing.domain.DueTerm;

import java.util.List;

public class BillFactory {

    public Bill createBill(ClientId clientId, String creationDate, DueTerm dueTerm, List<BillItem> billItems) {
        return new Bill(clientId, creationDate, dueTerm, billItems);
    }
}
