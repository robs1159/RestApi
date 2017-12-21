package ca.ulaval.glo4002.billing.infrastructure.persistence;

import ca.ulaval.glo4002.billing.domain.Payment;
import ca.ulaval.glo4002.billing.domain.repositories.PaymentRepository;

import javax.persistence.EntityManager;

public class HibernatePaymentRepository implements PaymentRepository {
    private final EntityManager entityManager;

    public HibernatePaymentRepository() {
        this.entityManager = new EntityManagerPaymentProvider().getEntityManager();
    }

    @Override
    public void insert(Payment payment) {
        entityManager.getTransaction().begin();
        entityManager.persist(payment);
        entityManager.getTransaction().commit();
    }
}
