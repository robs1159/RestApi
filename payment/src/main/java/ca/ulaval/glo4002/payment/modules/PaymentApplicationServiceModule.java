package ca.ulaval.glo4002.payment.modules;

import ca.ulaval.glo4002.payment.domain.payment.repositories.CrmRepository;
import ca.ulaval.glo4002.payment.domain.payment.repositories.PaymentRepository;
import ca.ulaval.glo4002.payment.infrastructure.persistence.CrmWrapper;
import ca.ulaval.glo4002.payment.infrastructure.persistence.HibernatePaymentRegistry;
import com.google.inject.AbstractModule;

public class PaymentApplicationServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(PaymentRepository.class).to(HibernatePaymentRegistry.class);
        bind(CrmRepository.class).to(CrmWrapper.class);
    }
}
