package com.rbkmoney.dudoser.converter;

import com.rbkmoney.damsel.payment_processing.EventPayload;
import com.rbkmoney.dudoser.exception.ParseException;
import com.rbkmoney.kafka.common.converter.BinaryConverter;
import com.rbkmoney.kafka.common.converter.BinaryConverterImpl;
import com.rbkmoney.machinegun.eventsink.MachineEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SourceEventParser {

    private final BinaryConverter<EventPayload> converter = new BinaryConverterImpl();

    public EventPayload parseEvent(MachineEvent message) {
        try {
            byte[] bin = message.getData().getBin();
            return converter.convert(bin, EventPayload.class);
        } catch (Exception e) {
            log.error("Exception when parse message e: ", e);
            throw new ParseException();
        }
    }
}
