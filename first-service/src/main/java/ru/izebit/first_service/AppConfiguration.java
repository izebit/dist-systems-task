package ru.izebit.first_service;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.izebit.common.config.AbstractConsumerConfig;
import ru.izebit.common.config.AbstractProducerConfig;
import ru.izebit.common.events.PingEvent;
import ru.izebit.common.events.PongEvent;

/**
 * @author <a href="mailto:izebit@gmail.com">Artem Konovalov</a> <br/>
 * Date: 28.10.2022
 */
@Configuration
public class AppConfiguration {

    @Configuration
    public static class ProducerConfig extends AbstractProducerConfig<PingEvent> {
    }

    @Configuration
    public static class ConsumerConfig extends AbstractConsumerConfig<PongEvent> {
    }
}
