package com.rbkmoney.dudoser.service;

import com.rbkmoney.dudoser.AbstractIntegrationTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by inal on 24.11.2016.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventServiceTest extends AbstractIntegrationTest {

    @Autowired
    EventService eventService;

    @Test
    public void test() throws Exception {
        eventService.setLastEventId(123L, 0);
        eventService.setLastEventId(123L, 0);
        assertEquals(123, eventService.getLastEventId(0).longValue());
        eventService.setLastEventId(124L, 1);
        assertEquals(124, eventService.getLastEventId(1).longValue());
    }
}
