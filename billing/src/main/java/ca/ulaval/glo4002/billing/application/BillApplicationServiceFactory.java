package ca.ulaval.glo4002.billing.application;

import ca.ulaval.glo4002.billing.modules.BillApplicationServiceModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class BillApplicationServiceFactory {

    public static BillApplicationService getBillApplicationService() {
        Injector injector = Guice.createInjector(new BillApplicationServiceModule());
        return injector.getInstance(BillApplicationService.class);
    }
}
