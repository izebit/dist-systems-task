package ru.izebit.first_service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;
import ru.izebit.common.events.PongEvent;

import javax.annotation.PostConstruct;

/**
 * @author <a href="mailto:izebit@gmail.com">Artem Konovalov</a> <br/>
 * Date: 28.10.2022
 */
@Service
@Log4j2
@AllArgsConstructor
public class ReceiverService {
    private final ReactiveKafkaConsumerTemplate<String, PongEvent> consumerTemplate;

    @PostConstruct
    public void receive() {
        consumerTemplate
                .receiveAutoAck()
                .map(ConsumerRecord::value)
                .doOnNext(event -> log.info("successfully consumed {}", event))
                .doOnError(ex -> log.error("something bad happened while consuming", ex))
                .subscribe();
    }
}
