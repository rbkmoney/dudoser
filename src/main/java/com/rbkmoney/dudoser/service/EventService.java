package com.rbkmoney.dudoser.service;

import com.rbkmoney.dudoser.exception.StorageException;
import com.rbkmoney.dudoser.utils.Converter;
import com.rbkmoney.dudoser.utils.FileHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class EventService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${file.pathToFolder}")
    private String pathToFolder;

    public Long getLastEventId() {
        Long lastEventId;
        try {
            log.trace("Get last event id");
            FileHelper.pathToFolder = pathToFolder;
            lastEventId = Converter.stringToLong(FileHelper.getLastEventId());
        } catch (IOException ex) {
            throw new StorageException("Failed to get last event id from file. Reason: " + ex.getMessage());
        }
        return lastEventId;
    }

}
