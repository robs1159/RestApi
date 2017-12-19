package ca.ulaval.glo4002.billing.modules;

import ca.ulaval.glo4002.billing.domain.bill.repositories.BillRepository;
import ca.ulaval.glo4002.billing.domain.bill.repositories.CrmRepository;
import ca.ulaval.glo4002.billing.infrastructure.persistence.CrmWrapper;
import ca.ulaval.glo4002.billing.infrastructure.persistence.HibernateBillRegistry;
import com.google.inject.AbstractModule;

public class BillApplicationServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(BillRepository.class).to(HibernateBillRegistry.class);
        bind(CrmRepository.class).to(CrmWrapper.class);
    }
}