package ca.ulaval.glo4002.billing.infrastructure.persistence;

import javax.persistence.EntityManager;

public class EntityManagerBillingProvider {

    private static ThreadLocal<EntityManager> localEntityManager = new ThreadLocal<>();

    public EntityManager getEntityManager() {
        return localEntityManager.get();
    }

    public static void setEntityManager(EntityManager entityManager) {
        localEntityManager.set(entityManager);
    }

    public static void clearEntityManager() {
        localEntityManager.set(null);
    }
}