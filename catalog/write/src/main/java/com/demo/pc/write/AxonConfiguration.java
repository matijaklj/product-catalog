package com.demo.pc.write;

import com.kumuluz.ee.kumuluzee.axon.ContainerManagedEntityManagerProvider;
import com.kumuluz.ee.kumuluzee.axon.transaction.JtaTransactionManager;
import org.axonframework.commandhandling.AsynchronousCommandBus;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.config.Configurer;
import org.axonframework.config.DefaultConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
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

    @Produces
    @ApplicationScoped
    public CommandBus getCommandBus(TransactionManager transactionManager) {
        return new AsynchronousCommandBus.Builder()
                .transactionManager(transactionManager)
                .build();
    }

    /**
     * Produces the entity manager provider.
     *
     * @return entity manager provider.
     */
    @Produces
    public EntityManagerProvider entityManagerProvider() {

        return new ContainerManagedEntityManagerProvider.Builder().build();
    }

    @Inject
    private CommandBus commandBus;

    @PreDestroy
    public void shutdown() {
        if (commandBus instanceof AsynchronousCommandBus)
            ((AsynchronousCommandBus) commandBus).shutdown();
    }
}
