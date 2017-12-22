package ca.ulaval.glo4002.billing.infrastructure.persistence;

import ca.ulaval.glo4002.billing.domain.Bill;
import ca.ulaval.glo4002.billing.domain.BillId;
import ca.ulaval.glo4002.billing.domain.ClientId;
import ca.ulaval.glo4002.billing.domain.Payment;
import ca.ulaval.glo4002.billing.domain.repositories.BillRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public class HibernateBillRepository implements BillRepository {
    private final EntityManager entityManager;

    public HibernateBillRepository() {
        this.entityManager = new EntityManagerBillingProvider().getEntityManager();
    }

    @Override
    public void insert(Bill bill) {
        entityManager.getTransaction().begin();
        entityManager.persist(bill);
        entityManager.getTransaction().commit();
    }

    @Override
    public void insertPayment(Payment payment) {
        entityManager.getTransaction().begin();
        entityManager.persist(payment);
        entityManager.getTransaction().commit();
    }

    @Override
    public void update(Bill bill) {
        entityManager.getTransaction().begin();
        entityManager.persist(bill);
        entityManager.getTransaction().commit();
    }

    @Override
    public Optional<Bill> findBillById(BillId billId) {
        Optional<Bill> bill = Optional.empty();

        try {
            String query = "select b from Bill b, BillId bid where b.billId = bid and bid.billId = :billId";
            bill = Optional.ofNullable(entityManager.createQuery(query, Bill.class).setParameter("billId", billId.getId()).getSingleResult());
        } catch (NoResultException nre) {
        }

        return bill;
    }

    @Override
    public List<Bill> findBillsByClientIdOrderedByOldestExpectedPayment(ClientId clientId) {
        List<Bill> clientBills = null;

        try {
            String queryString = "select b from Bill b, ClientId cId where b.clientId = cId and cId.clientId = :clientId and b.effectiveDate <> null order by b.expectedPayment desc";
            clientBills = entityManager.createQuery(queryString, Bill.class).setParameter("clientId", clientId.getClientId()).getResultList();

        } catch (NoResultException nre) {
        }

        return clientBills;
    }

    @Override
    public List<Payment> findPaymentsByDate(ZonedDateTime startDate, ZonedDateTime endDate) {
        List<Payment> inDatePayment = null;

        try {
            //String queryString = "select p from Payment p, ClientId cId where p.clientId = cId and p.date > :startDate and p.date < :endDate order by p.date desc";
            //inDatePayment = entityManager.createQuery(queryString, Payment.class).setParameter("startDate", startDate).setParameter("endDate", endDate).getResultList();

            String queryString = "select p from Payment p, ClientId cId where p.clientId = cId order by p.date desc";
            inDatePayment = entityManager.createQuery(queryString, Payment.class).getResultList();

        } catch (NoResultException nre) {
        }

        return inDatePayment;
    }

    @Override
    public List<Bill> findBillsByExpectedPayment(ZonedDateTime startDate, ZonedDateTime endDate) {
        List<Bill> inDateBills = null;

        try {
            //String queryString = "select b from Bill b, ClientId cId where b.clientId = cId and b.effectiveDate > :startDate and b.effectiveDate < :endDate and b.effectiveDate <> null order by b.expectedPayment desc";
            //inDateBills = entityManager.createQuery(queryString, Bill.class).setParameter("startDate", startDate).setParameter("endDate", endDate).getResultList();
            String queryString = "select b from Bill b, ClientId cId where b.clientId = cId and b.effectiveDate <> null order by b.expectedPayment desc";
            inDateBills = entityManager.createQuery(queryString, Bill.class).getResultList();

        } catch (NoResultException nre) {
        }

        return inDateBills;
    }
}
