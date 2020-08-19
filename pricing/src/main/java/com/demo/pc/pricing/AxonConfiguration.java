package com.demo.pc.pricing;

import org.axonframework.config.Configurer;
import org.axonframework.config.DefaultConfigurer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class AxonConfiguration {

    @Produces
    @ApplicationScoped
    public Configurer configurer() {
        Configurer configurer = DefaultConfigurer.defaultConfiguration();

        return configurer;
    }
}
