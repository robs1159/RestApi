package ca.ulaval.glo4002.billing.modules;

import ca.ulaval.glo4002.billing.application.repositories.crm.CrmClientRepository;
import ca.ulaval.glo4002.billing.application.repositories.crm.CrmProductRepository;
import ca.ulaval.glo4002.billing.domain.repositories.BillRepository;
import ca.ulaval.glo4002.billing.domain.repositories.ClientRepository;
import ca.ulaval.glo4002.billing.domain.repositories.ProductRepository;
import ca.ulaval.glo4002.billing.infrastructure.persistence.HibernateBillRepository;
import com.google.inject.AbstractModule;

public class BillApplicationServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(BillRepository.class).to(HibernateBillRepository.class);
        bind(ClientRepository.class).to(CrmClientRepository.class);
        bind(ProductRepository.class).to(CrmProductRepository.class);
    }
}