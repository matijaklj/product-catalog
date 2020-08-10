package com.demo.pc.write;

import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.common.jpa.SimpleEntityManagerProvider;
import org.axonframework.common.transaction.NoTransactionManager;
import org.axonframework.common.transaction.Transaction;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.config.Configurer;
import org.axonframework.config.DefaultConfigurer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.ApplicationPath;

@ApplicationScoped
public class AxonConfiguration {

    private final String JBOSS_USER_TRANSACTION = "java:comp/UserTransaction";

    @Produces
    @ApplicationScoped
    public Configurer configurer() {
        Configurer configurer = DefaultConfigurer.defaultConfiguration();

        return configurer;
    }

    @Produces
    @ApplicationScoped
    public TransactionManager transactionManager(EntityManager em) {
        return new ContainerTransactionManager(em, JBOSS_USER_TRANSACTION);
        //return NoTransactionManager.instance();// new JtaTransactionManager();
    }

    /**
     * Produces the entity manager.
     *
     * @return entity manager.
     */
    @Produces
    @ApplicationScoped
    public EntityManager entityManager() {
        try {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("product-catalog-write");
            EntityManager em = factory.createEntityManager();

            return em;
        } catch (Exception ex) {
            System.out.println("Failed to look up entity manager. " + ex.getMessage());
        }

        return null;
    }

    /**
     * Produces the entity manager provider.
     *
     * @return entity manager provider.
     */
    @Produces
    @ApplicationScoped
    public EntityManagerProvider entityManagerProvider(
            EntityManager entityManager) {
        return new SimpleEntityManagerProvider(entityManager);
    }
}
