package ca.ulaval.glo4002.billing.domain.repositories;

import ca.ulaval.glo4002.billing.domain.Bill;
import ca.ulaval.glo4002.billing.domain.BillId;
import ca.ulaval.glo4002.billing.domain.ClientId;
import ca.ulaval.glo4002.billing.domain.Payment;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface BillRepository {
    void insert(Bill bill);

    void insertPayment(Payment payment);

    void update(Bill bill);

    Optional<Bill> findBillById(BillId billId);

    List<Bill> findBillsByClientIdOrderedByOldestExpectedPayment(ClientId clientId);

    List<Payment> findPaymentsByDate(ZonedDateTime startDate, ZonedDateTime endDate);

    List<Bill> findBillsByExpectedPayment(ZonedDateTime startDate, ZonedDateTime endDate);
}
