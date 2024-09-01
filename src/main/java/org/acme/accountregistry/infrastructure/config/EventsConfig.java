package org.acme.accountregistry.infrastructure.config;

import static java.lang.Long.parseLong;
import static java.util.concurrent.TimeUnit.SECONDS;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;

@Configuration
class EventsConfig {

    /**
     * Set up for the Spring framework to publish and handle events asynchronously
     */
    @Bean
    ApplicationEventMulticaster applicationEventMulticaster(
      @Value("${spring.lifecycle.timeout-per-shutdown-phase}") final String timeOutPerShutDown) {
        final var asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setVirtualThreads(true);
        final long taskTerminationTimeOut = parseLong(timeOutPerShutDown.replaceAll("[^0-9]", ""));
        asyncTaskExecutor.setTaskTerminationTimeout(SECONDS.toMillis(taskTerminationTimeOut));

        final var eventMulticaster = new SimpleApplicationEventMulticaster();
        eventMulticaster.setTaskExecutor(asyncTaskExecutor);

        return eventMulticaster;
    }

    @Bean
    AuthenticationEventPublisher authenticationEventPublisher(final ApplicationEventPublisher applicationEventPublisher) {
        return new DefaultAuthenticationEventPublisher(applicationEventPublisher);
    }
}
