package ca.ulaval.glo4002.billing.infrastructure.persistence;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryBillingProvider {

    private static final String PERSISTENCE_UNIT_NAME = "billing";
    private static EntityManagerFactory instance;

    public static EntityManagerFactory getFactory() {
        if (instance == null) {
            instance = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
        return instance;
    }
}