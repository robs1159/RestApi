package ca.ulaval.glo4002.billing.infrastructure.persistence;

import ca.ulaval.glo4002.billing.domain.Bill;
import ca.ulaval.glo4002.billing.domain.BillId;
import ca.ulaval.glo4002.billing.domain.ClientId;
import ca.ulaval.glo4002.billing.domain.repositories.BillRepository;

import java.util.*;

//TODO: faire des tests unitaire
public class InMemoryBillRepository implements BillRepository {
    private static final Map<BillId, Bill> BILLS = new HashMap<>();

    @Override
    public void insert(Bill bill) {
        BILLS.put(bill.getId(), bill);
    }

    @Override
    public void update(Bill bill) {
        BILLS.replace(bill.getId(), bill);
    }

    @Override
    public Optional<Bill> findBillById(BillId billId) {
        Bill bill = BILLS.get(billId);
        return Optional.ofNullable(bill);
    }

    @Override
    public List<Bill> findBillsByClientIdOrderedByOldestExpectedPayment(ClientId clientId) {
        List<Bill> clientBills = new ArrayList<>();

        for (Map.Entry<BillId, Bill> billEntry : BILLS.entrySet()) {
            Bill bill = billEntry.getValue();

            if (bill.getClientId().equals(clientId) && bill.getEffectiveDate() != null) {
                clientBills.add(bill);
            }
        }

        clientBills.sort(Collections.reverseOrder(Comparator.comparing(Bill::getExpectedPayment)));

        return clientBills;
    }
}
