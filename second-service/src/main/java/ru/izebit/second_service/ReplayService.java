package ru.izebit.second_service;

import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;
import ru.izebit.common.events.PingEvent;
import ru.izebit.common.events.PongEvent;

/**
 * @author <a href="mailto:izebit@gmail.com">Artem Konovalov</a> <br/>
 * Date: 28.10.2022
 */
@Service
@Log4j2
public class ReplayService {
    private final ReactiveKafkaConsumerTemplate<String, PingEvent> consumerTemplate;
    private final ReactiveKafkaProducerTemplate<String, PongEvent> producerTemplate;
    private final String outputTopic;

    public ReplayService(final ReactiveKafkaConsumerTemplate<String, PingEvent> consumerTemplate,
                         final ReactiveKafkaProducerTemplate<String, PongEvent> producerTemplate,
                         @Value("${spring.kafka.producer.topic}") final String outputTopic) {
        this.consumerTemplate = consumerTemplate;
        this.producerTemplate = producerTemplate;
        this.outputTopic = outputTopic;
    }

    public void replay() {
        consumerTemplate
                .receiveAutoAck()
                .map(ConsumerRecord::value)
                .map(event -> new PongEvent())
                .flatMap(event -> producerTemplate.send(outputTopic, event))
                .doOnError(ex -> log.error("something bad happened while consuming", ex))
                .blockLast();
    }
}
