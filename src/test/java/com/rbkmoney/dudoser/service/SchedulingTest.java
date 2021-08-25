package com.rbkmoney.dudoser.service;

import com.rbkmoney.dudoser.dao.MessageDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ScheduledMailHandlerServiceTest.Config.class)
@TestPropertySource(properties = {
        "message.store.days=1",
        "notification.payment.paid.from=test",
        "message.schedule.send=100",
        "message.schedule.clear.sent=100",
        "message.schedule.clear.failed=100",
        "message.fail.minutes=5"
})
@EnableScheduling
public class SchedulingTest {

    @Autowired
    MessageDao messageDao;


    @Test
    public void test() throws InterruptedException {
        Thread.sleep(1000);

        verify(messageDao, atLeastOnce()).getUnsentMessages();
        verify(messageDao, atLeastOnce()).deleteMessages(any(), eq(true));
        verify(messageDao, atLeastOnce()).deleteMessages(any(), eq(false));
    }

}
