package com.rbkmoney.dudoser.kafka;

import com.rbkmoney.damsel.payment_processing.EventPayload;
import com.rbkmoney.damsel.payment_processing.InvoiceChange;
import com.rbkmoney.dudoser.configuration.KafkaConfig;
import com.rbkmoney.dudoser.listener.InvoicingKafkaListener;
import com.rbkmoney.dudoser.service.HandlerManager;
import com.rbkmoney.kafka.common.serialization.ThriftSerializer;
import com.rbkmoney.machinegun.eventsink.MachineEvent;
import com.rbkmoney.machinegun.eventsink.SinkEvent;
import com.rbkmoney.machinegun.msgpack.Value;
import com.rbkmoney.sink.common.parser.impl.MachineEventParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@Slf4j
@ContextConfiguration(classes = {KafkaConfig.class, InvoicingKafkaListener.class})
public class InvoicingKafkaListenerTest extends AbstractKafkaTest {

    @org.springframework.beans.factory.annotation.Value("${kafka.topics.invoice.id}")
    public String topic;

    @org.springframework.beans.factory.annotation.Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

    @MockBean
    HandlerManager handlerManager;

    @MockBean
    MachineEventParser eventParser;

    @Test
    public void listenChanges() throws InterruptedException {
        when(eventParser.parse(any())).thenReturn(EventPayload.invoice_changes(List.of(new InvoiceChange())));

        SinkEvent sinkEvent = new SinkEvent();
        sinkEvent.setEvent(createMessage());

        writeToTopic(sinkEvent);

        waitForTopicSync();

        Mockito.verify(eventParser, Mockito.timeout(100000).times(1)).parse(any());
        Mockito.verify(handlerManager, Mockito.timeout(100000).times(1)).getHandler(any());
    }

    private void writeToTopic(SinkEvent sinkEvent) {
        Producer<String, SinkEvent> producer = createProducer();
        ProducerRecord<String, SinkEvent> producerRecord = new ProducerRecord<>(topic, null, sinkEvent);
        try {
            producer.send(producerRecord).get();
        } catch (Exception e) {
            log.error("KafkaAbstractTest initialize e: ", e);
        }
        producer.close();
    }

    private void waitForTopicSync() throws InterruptedException {
        Thread.sleep(1000L);
    }


    private MachineEvent createMessage() {
        MachineEvent message = new MachineEvent();
        Value data = new Value();
        data.setBin(new byte[0]);
        message.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        message.setEventId(1L);
        message.setSourceNs("sad");
        message.setSourceId("sda");
        message.setData(data);
        return message;
    }

    private Producer<String, SinkEvent> createProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "client_id");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, new ThriftSerializer<SinkEvent>().getClass());
        return new KafkaProducer<>(props);
    }

}
