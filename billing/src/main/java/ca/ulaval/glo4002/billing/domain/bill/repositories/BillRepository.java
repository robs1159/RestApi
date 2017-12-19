package ca.ulaval.glo4002.billing.domain.bill.repositories;

import ca.ulaval.glo4002.billing.domain.bill.Bill;
import ca.ulaval.glo4002.billing.domain.bill.BillId;
import ca.ulaval.glo4002.crmInterface.domain.ClientId;

import java.util.List;
import java.util.Optional;

public interface BillRepository {
    void insert(Bill bill);

    void update(Bill bill);

    Optional<Bill> findBillById(BillId billId);

    List<Bill> findBillsByClientIdOrderedByOldestExpectedPayment(ClientId clientId);
}
