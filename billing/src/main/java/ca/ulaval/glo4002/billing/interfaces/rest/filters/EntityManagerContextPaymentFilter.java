package ca.ulaval.glo4002.billing.interfaces.rest.filters;

import ca.ulaval.glo4002.billing.infrastructure.persistence.EntityManagerFactoryPaymentProvider;
import ca.ulaval.glo4002.billing.infrastructure.persistence.EntityManagerPaymentProvider;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.*;
import java.io.IOException;

public class EntityManagerContextPaymentFilter implements Filter {

    private EntityManagerFactory entityManagerFactory;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        entityManagerFactory = EntityManagerFactoryPaymentProvider.getFactory();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        EntityManager entityManager = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            EntityManagerPaymentProvider.setEntityManager(entityManager);
            chain.doFilter(request, response);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
            EntityManagerPaymentProvider.clearEntityManager();
        }
    }

    @Override
    public void destroy() {
        entityManagerFactory.close();
    }
}