package ca.ulaval.glo4002.billing.infrastructure.persistence;

import ca.ulaval.glo4002.billing.domain.Bill;
import ca.ulaval.glo4002.billing.domain.BillId;
import ca.ulaval.glo4002.billing.domain.ClientId;
import ca.ulaval.glo4002.billing.domain.repositories.BillRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
}
