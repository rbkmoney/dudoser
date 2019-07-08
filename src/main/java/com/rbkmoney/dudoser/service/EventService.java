package com.rbkmoney.dudoser.service;

import com.rbkmoney.dudoser.dao.DaoException;
import com.rbkmoney.dudoser.dao.LastEventDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class EventService {

    private final LastEventDao lastEventDao;

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
