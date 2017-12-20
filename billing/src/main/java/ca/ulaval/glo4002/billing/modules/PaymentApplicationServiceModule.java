package ca.ulaval.glo4002.billing.modules;

import ca.ulaval.glo4002.billing.domain.repositories.PaymentRepository;
import ca.ulaval.glo4002.billing.infrastructure.persistence.HibernatePaymentRegistry;
import com.google.inject.AbstractModule;

public class PaymentApplicationServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(PaymentRepository.class).to(HibernatePaymentRegistry.class);
    }
}
