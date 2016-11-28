package com.rbkmoney.dudoser.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by inal on 24.11.2016.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Component
public class EventServiceTest {

    @Autowired
    EventService eventService;

    @Test
    public void getLastEventId() throws Exception {

        Long lastEventId = eventService.getLastEventId();
        if (lastEventId != null) {
            assertEquals(lastEventId, eventService.getLastEventId());
        }
    }

}