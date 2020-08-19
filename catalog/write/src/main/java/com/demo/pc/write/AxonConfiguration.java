package com.demo.pc.write;

import com.kumuluz.ee.kumuluzee.axon.ContainerManagedEntityManagerProvider;
import com.kumuluz.ee.kumuluzee.axon.transaction.JtaTransactionManager;
import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.config.Configurer;
import org.axonframework.config.DefaultConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.lang.invoke.MethodHandles;

@ApplicationScoped
public class AxonConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());

    @Produces
    @ApplicationScoped
    public Configurer configurer() {
        Configurer configurer = DefaultConfigurer.defaultConfiguration();

        return configurer;
    }

    @Produces
    @ApplicationScoped
    public TransactionManager transactionManager() {
        return new JtaTransactionManager();
    }

    /**
     * Produces the entity manager.
     *
     * @return entity manager.

    @Produces
    public EntityManager entityManager() {

        try {
            InitialContext ctx = new InitialContext();
            DataSource dcDataSource = (DataSource) ctx.lookup("jdbc/productCatalogDS");

            Connection c = dcDataSource.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        try {
            //EntityManager em = emf.createEntityManager();
            Context ctx = new InitialContext(System.getProperties());
            Object ref = ctx.lookup("testEM");

            return (EntityManager) ref;
        } catch (Exception ex) {
            logger.error("Failed to look up entity manager. " + ex.getMessage());
        }

        return null;
    }*/

    /**
     * Produces the entity manager provider.
     *
     * @return entity manager provider.
     */
    @Produces
    public EntityManagerProvider entityManagerProvider() {

        return new ContainerManagedEntityManagerProvider.Builder().build();
    }
}
