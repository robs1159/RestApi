package ca.ulaval.glo4002.billing.application;

import ca.ulaval.glo4002.billing.modules.PaymentApplicationServiceModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class PaymentApplicationServiceFactory {

    public PaymentApplicationService getPaymentApplicationService() {
        Injector injector = Guice.createInjector(new PaymentApplicationServiceModule());
        return injector.getInstance(PaymentApplicationService.class);
    }
}