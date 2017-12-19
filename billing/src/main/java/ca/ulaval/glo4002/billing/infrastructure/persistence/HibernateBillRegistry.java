package ca.ulaval.glo4002.billing.infrastructure.persistence;

import ca.ulaval.glo4002.billing.domain.bill.Bill;
import ca.ulaval.glo4002.billing.domain.bill.BillId;
import ca.ulaval.glo4002.billing.domain.bill.repositories.BillRepository;
import ca.ulaval.glo4002.crmInterface.domain.ClientId;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class HibernateBillRegistry implements BillRepository {

    private final EntityManager entityManager;

    public HibernateBillRegistry() {
        this.entityManager = new EntityManagerProvider().getEntityManager();
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
            bill = Optional.ofNullable(entityManager.createQuery(query, Bill.class).setParameter("billId", billId.getBillId()).getSingleResult());
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

    public Collection<Bill> findAllOBills() {
        String query = "select b from Bill b";
        return entityManager.createQuery(query, Bill.class).getResultList();
    }
}
