package com.rbkmoney.dudoser.service;

import com.rbkmoney.dudoser.dao.LastEventDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    LastEventDao lastEventDao;

    public Long getLastEventId() {
        Long lastEventId = lastEventDao.get();
        log.info("Get last event id = {}", lastEventId);
        return lastEventId;
    }

    public void setLastEventId(Long id) {
        lastEventDao.set(id);
        log.info("Set last event id = {}", id);
    }

}
