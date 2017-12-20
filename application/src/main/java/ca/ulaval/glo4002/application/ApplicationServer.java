package ca.ulaval.glo4002.application;

import ca.ulaval.glo4002.billing.BillingServer;
import ca.ulaval.glo4002.billing.interfaces.rest.filters.EntityManagerContextFilter;
import ca.ulaval.glo4002.crm.CrmServer;

public class ApplicationServer {

    public static void main(String[] args) throws Exception {
        BillingServer billingServer = new BillingServer();

        Thread crm = new Thread(new CrmServer(args));
        Thread billing = new Thread(billingServer);

        billing.start();
        crm.start();

        billing.join();

        billingServer.addFilterToCurrentServer(EntityManagerContextFilter.class);
        billingServer.startServer();
    }
}
