package com.rbkmoney.dudoser;

import com.rbkmoney.damsel.payment_processing.Event;
import com.rbkmoney.damsel.payment_processing.EventPayload;
import com.rbkmoney.damsel.payment_processing.InvoiceChange;
import com.rbkmoney.dudoser.exception.ParseException;
import com.rbkmoney.dudoser.handler.poller.InvoiceCreatedHandler;
import com.rbkmoney.dudoser.listener.InvoicingKafkaListener;
import com.rbkmoney.dudoser.service.HandlerManager;
import com.rbkmoney.machinegun.eventsink.MachineEvent;
import com.rbkmoney.machinegun.eventsink.SinkEvent;
import com.rbkmoney.machinegun.msgpack.Value;
import com.rbkmoney.sink.common.parser.impl.MachineEventParser;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.support.Acknowledgment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Matchers.any;

public class InvoicingListenerTest {

    @Mock
    private HandlerManager handlerManager;
    @Mock
    private MachineEventParser<EventPayload> eventParser;
    @Mock
    private Acknowledgment ack;
    @Mock
    private InvoiceCreatedHandler handler;

    private InvoicingKafkaListener listener;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        listener = new InvoicingKafkaListener(handlerManager, eventParser);
    }

    @Test
    public void listenNonInvoiceChanges() {

        MachineEvent message = new MachineEvent();
        Event event = new Event();
        EventPayload payload = new EventPayload();
        payload.setCustomerChanges(List.of());
        event.setPayload(payload);
        Mockito.when(eventParser.parse(message)).thenReturn(payload);

        SinkEvent sinkEvent = new SinkEvent();
        sinkEvent.setEvent(message);

        listener.handle(sinkEvent, ack);

        Mockito.verify(handlerManager, Mockito.times(0)).getHandler(any());
        Mockito.verify(ack, Mockito.times(1)).acknowledge();
    }

    @Test(expected = ParseException.class)
    public void listenEmptyException() {
        MachineEvent message = new MachineEvent();

        SinkEvent sinkEvent = new SinkEvent();
        sinkEvent.setEvent(message);

        Mockito.when(eventParser.parse(message)).thenThrow(new ParseException());

        listener.handle(sinkEvent, ack);

        Mockito.verify(ack, Mockito.times(0)).acknowledge();
    }

    @Test
    public void listenChanges() {
        MachineEvent message = new MachineEvent();
        Event event = new Event();
        EventPayload payload = new EventPayload();
        ArrayList<InvoiceChange> invoiceChanges = new ArrayList<>();
        invoiceChanges.add(new InvoiceChange());
        payload.setInvoiceChanges(invoiceChanges);
        event.setPayload(payload);
        Mockito.when(eventParser.parse(message)).thenReturn(payload);

        Mockito.when(handlerManager.getHandler(any())).thenReturn(Optional.of(handler));

        SinkEvent sinkEvent = new SinkEvent();
        sinkEvent.setEvent(message);

        listener.handle(sinkEvent, ack);

        Mockito.verify(handlerManager, Mockito.times(1)).getHandler(any());
        Mockito.verify(ack, Mockito.times(1)).acknowledge();
    }

    private static SinkEvent createSinkEvent() {
        MachineEvent message = new MachineEvent();
        Value data = new Value();
        data.setBin(new byte[0]);
        message.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        message.setEventId(1L);
        message.setSourceNs("ns");
        message.setSourceId("id");
        message.setData(data);
        return new SinkEvent();
    }
}
