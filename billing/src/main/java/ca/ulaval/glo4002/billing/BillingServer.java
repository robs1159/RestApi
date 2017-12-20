package ca.ulaval.glo4002.billing;

import ca.ulaval.glo4002.billing.interfaces.rest.exception_mapper.BillAlreadyAcceptedExceptionMapper;
import ca.ulaval.glo4002.billing.interfaces.rest.exception_mapper.BillNotFoundExceptionMapper;
import ca.ulaval.glo4002.billing.interfaces.rest.exception_mapper.ClientNotFoundExceptionMapper;
import ca.ulaval.glo4002.billing.interfaces.rest.exception_mapper.ProductNotFoundExceptionMapper;
import ca.ulaval.glo4002.billing.interfaces.rest.filters.CharsetResponseFilter;
import ca.ulaval.glo4002.billing.interfaces.rest.filters.EntityManagerContextFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.EnumSet;

public class BillingServer implements Runnable {
    private static final int PORT = 8181;
    private static final String PATH_SPEC = "/*";
    private static ServletContextHandler contextHandler;
    private static Server server;

    public static void main(String[] args) {
        new BillingServer().run();
    }

    @Override
    public void run() {
        server = new Server(PORT);
        contextHandler = new ServletContextHandler(server, "/");

        ResourceConfig packageConfig = new ResourceConfig().packages("ca.ulaval.glo4002.billing");

        packageConfig.register(new CharsetResponseFilter());
        packageConfig.registerInstances(new ClientNotFoundExceptionMapper(), new ProductNotFoundExceptionMapper(), new BillAlreadyAcceptedExceptionMapper(), new BillNotFoundExceptionMapper());

        ServletContainer container = new ServletContainer(packageConfig);
        ServletHolder servletHolder = new ServletHolder(container);

        contextHandler.addFilter(EntityManagerContextFilter.class, PATH_SPEC, EnumSet.of(DispatcherType.REQUEST));
        contextHandler.addServlet(servletHolder, PATH_SPEC);
    }

    public void addFilterToCurrentServer(Class<? extends Filter> filter) {
        contextHandler.addFilter(filter, PATH_SPEC, EnumSet.of(DispatcherType.REQUEST));
    }

    public void startServer() {
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.destroy();
        }
    }
}