package com.rbkmoney.dudoser.listener;

import com.rbkmoney.damsel.payment_processing.EventPayload;
import com.rbkmoney.damsel.payment_processing.InvoiceChange;
import com.rbkmoney.dudoser.service.HandlerManager;
import com.rbkmoney.machinegun.eventsink.SinkEvent;
import com.rbkmoney.sink.common.parser.impl.MachineEventParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;

@Slf4j
@RequiredArgsConstructor
public class InvoicingKafkaListener {

    private final HandlerManager handlerManager;
    private final MachineEventParser<EventPayload> parser;

    @KafkaListener(topics = "${kafka.topics.invoice.id}", containerFactory = "kafkaListenerContainerFactory")
    public void handle(SinkEvent sinkEvent, Acknowledgment ack) {
        log.debug("Reading sinkEvent, sourceId:{}, eventId:{}", sinkEvent.getEvent().getSourceId(), sinkEvent.getEvent().getEventId());
        EventPayload payload = parser.parse(sinkEvent.getEvent());
        if (payload.isSetInvoiceChanges()) {
            for (InvoiceChange invoiceChange : payload.getInvoiceChanges()) {
                try {
                    handlerManager.getHandler(invoiceChange)
                            .ifPresentOrElse(handler -> handler.handle(invoiceChange, sinkEvent.getEvent().getSourceId()),
                                    () -> log.debug("Handler for invoiceChange {} wasn't found (machineEvent {})", invoiceChange, sinkEvent.getEvent()));
                } catch (Exception ex) {
                    log.error("Failed to handle invoice change, invoiceChange='{}'", invoiceChange, ex);
                    throw ex;
                }
            }
        }
        ack.acknowledge();
    }
}