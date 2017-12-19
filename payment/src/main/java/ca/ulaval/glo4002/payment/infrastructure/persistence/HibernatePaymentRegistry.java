package ca.ulaval.glo4002.payment.infrastructure.persistence;

import ca.ulaval.glo4002.payment.domain.payment.Payment;
import ca.ulaval.glo4002.payment.domain.payment.PaymentId;
import ca.ulaval.glo4002.payment.domain.payment.repositories.PaymentRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Collection;
import java.util.Optional;

public class HibernatePaymentRegistry implements PaymentRepository
{
    private final EntityManager entityManager;

    public HibernatePaymentRegistry() {
        this.entityManager = new EntityManagerPaymentProvider().getEntityManager();
    }

    @Override
    public void insert(Payment payment) {
        entityManager.getTransaction().begin();
        entityManager.persist(payment);
        entityManager.getTransaction().commit();
    }

    @Override
    public void update(Payment payment) {
        entityManager.getTransaction().begin();
        entityManager.persist(payment);
        entityManager.getTransaction().commit();
    }

    @Override
    public Optional<Payment> findPaymentById(PaymentId paymentId) {
        Optional<Payment> payment = Optional.empty();

        try {
            String query = "select p from Payment p, PaymentId pid where p.paymentId = pid and pid.paymentId = :paymentId";
            payment = Optional.ofNullable(entityManager.createQuery(query, Payment.class).setParameter("paymentId", paymentId.getUniqueId()).getSingleResult());
        } catch (NoResultException nre) {
        }

        return payment;
    }

    public Collection<Payment> findAllOPayment() {
        String query = "select p from Payment p";
        return entityManager.createQuery(query, Payment.class).getResultList();
    }
}
