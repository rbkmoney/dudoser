package com.rbkmoney.dudoser.service;

import com.rbkmoney.dudoser.dao.DaoException;
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

    public Long getLastEventId(int id) {
        Long lastEventId = lastEventDao.get(id);
        log.info("Get lastEventId = {} with id {}", lastEventId, id);
        return lastEventId;
    }

    public void setLastEventId(Long eventId, int id) {
        try {
            lastEventDao.set(eventId, id);
            log.info("Set lastEventId {} with id {}", eventId, id);
        } catch (DaoException e) {
            log.error("Couldn't set last event with id {}", eventId, e);
        }
    }
}
