package com.rbkmoney.dudoser.listener;

import com.rbkmoney.machinegun.eventsink.SinkEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaMachineEventListener {

    private final MachineEventHandler machineEventHandler;

    @KafkaListener(topics = "${kafka.invoice.topic}", containerFactory = "kafkaListenerContainerFactory")
    public void listen(SinkEvent message, Acknowledgment ack) {
        log.debug("Got machineEvent: {}", message);
        machineEventHandler.handle(message.getEvent(), ack);
        log.debug("Handled machineEvent {}", message);
    }

}

