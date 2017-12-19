package ca.ulaval.glo4002.payment.domain.payment.repositories;

import ca.ulaval.glo4002.payment.domain.payment.Payment;
import ca.ulaval.glo4002.payment.domain.payment.PaymentId;

import java.util.Collection;
import java.util.Optional;

public interface PaymentRepository {
    void insert(Payment payment);

    void update(Payment payment);

    Optional<Payment> findPaymentById(PaymentId paymentId);

    Collection<Payment> findAllOPayment();
}
