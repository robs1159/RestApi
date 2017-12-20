package ca.ulaval.glo4002.billing.domain.repositories;

import ca.ulaval.glo4002.billing.domain.Payment;

public interface PaymentRepository {

    void insert(Payment payment);
}
