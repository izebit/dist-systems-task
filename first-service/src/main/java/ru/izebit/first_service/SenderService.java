package ru.izebit.first_service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import ru.izebit.common.events.PingEvent;

import java.time.Duration;

/**
 * @author <a href="mailto:izebit@gmail.com">Artem Konovalov</a> <br/>
 * Date: 28.10.2022
 */
@Service
@Log4j2
public class SenderService {
    private final ReactiveKafkaProducerTemplate<String, PingEvent> producerTemplate;
    private final String topic;

    public SenderService(final ReactiveKafkaProducerTemplate<String, PingEvent> producerTemplate,
                         @Value(value = "${spring.kafka.producer.topic}") final String topic) {
        this.producerTemplate = producerTemplate;
        this.topic = topic;
    }

    public void startSending() {
        Flux
                .interval(Duration.ofSeconds(1))
                .map(e -> new PingEvent())
                .flatMap(event -> {
                    log.info("send to topic={}, {},", topic, event);
                    return producerTemplate.send(topic, event);
                })
                .blockLast();
    }
}
