package ca.ulaval.glo4002.payment.infrastructure.persistence;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryPaymentProvider {

    private static final String PERSISTENCE_UNIT_NAME = "payment";
    private static EntityManagerFactory instance;

    public static EntityManagerFactory getFactory() {
        if (instance == null) {
            instance = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
        return instance;
    }
}