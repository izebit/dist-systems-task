package ru.izebit.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.Collections;

/**
 * @author <a href="mailto:izebit@gmail.com">Artem Konovalov</a> <br/>
 * Date: 28.10.2022
 */
public abstract class AbstractConsumerConfig<Res> {
    @Bean
    public ReceiverOptions<String, Res> kafkaReceiverOptions(@Value(value = "${spring.kafka.consumer.topic}") String topic,
                                                             KafkaProperties kafkaProperties) {
        ReceiverOptions<String, Res> basicReceiverOptions = ReceiverOptions.create(kafkaProperties.buildConsumerProperties());
        return basicReceiverOptions.subscription(Collections.singletonList(topic));
    }

    @Bean
    public ReactiveKafkaConsumerTemplate<String, Res> reactiveKafkaConsumerTemplate(
            ReceiverOptions<String, Res> kafkaReceiverOptions) {
        return new ReactiveKafkaConsumerTemplate<>(kafkaReceiverOptions);
    }
}