package com.demo.pc.read;

import com.kumuluz.ee.kumuluzee.axon.ContainerManagedEntityManagerProvider;
import com.kumuluz.ee.kumuluzee.axon.transaction.JtaTransactionManager;
import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.config.Configurer;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.eventhandling.tokenstore.jpa.JpaTokenStore;
import org.axonframework.serialization.Serializer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class AxonConfiguration {

    @Produces
    @ApplicationScoped
    public TransactionManager transactionManager() {
        return new JtaTransactionManager();
    }

    @Produces
    @ApplicationScoped
    public TokenStore configureTokenStore(EntityManagerProvider entityManagerProvider, Serializer s) {
        return JpaTokenStore.builder()
                .entityManagerProvider(entityManagerProvider)
                .serializer(s)
                .build();
    }

    @Produces
    @ApplicationScoped
    public Configurer configurer() {
        Configurer configurer = DefaultConfigurer.defaultConfiguration();

        return configurer;
    }

    @Produces
    @ApplicationScoped
    public EntityManagerProvider entityManagerProvider() {
        return new ContainerManagedEntityManagerProvider.Builder().build();
    }
}
