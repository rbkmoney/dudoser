package com.rbkmoney.dudoser.configuration;

import com.rbkmoney.damsel.payment_processing.EventPayload;
import com.rbkmoney.dudoser.listener.InvoicingKafkaListener;
import com.rbkmoney.dudoser.service.HandlerManager;
import com.rbkmoney.sink.common.parser.impl.MachineEventParser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@Configuration
@EnableKafka
public class KafkaConsumerBeanEnableConfig {

    @Bean
    @ConditionalOnProperty(value = "kafka.topics.invoice.enabled", havingValue = "true")
    public InvoicingKafkaListener paymentEventsKafkaListener(HandlerManager handlerManager,
                                                             MachineEventParser<EventPayload> parser) {
        return new InvoicingKafkaListener(handlerManager, parser);
    }
}