package com.rbkmoney.dudoser.service;

import com.rbkmoney.dudoser.dao.MessageDao;
import com.rbkmoney.dudoser.dao.model.MessageToSend;
import com.rbkmoney.dudoser.exception.MailNotSendException;
import com.rbkmoney.dudoser.exception.MessageStoreException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ScheduledMailHandlerServiceTest.Config.class)
@TestPropertySource(properties = {
        "message.store.days=1",
        "notification.payment.paid.from=test",
        "message.schedule.send=1000",
        "message.schedule.clear=1000"
})
public class ScheduledMailHandlerServiceTest {

    @Autowired
    ScheduledMailHandlerService service;

    @Autowired
    MessageDao messageDao;

    @Autowired
    MailSenderService mailSenderService;

    @Test
    public void storeSuccess() {
        when(messageDao.store(any(), any(), any())).thenReturn(true);
        service.storeMessage("test", "test", "test");

        verify(messageDao, times(1)).store(any(), any(), any());
    }

    @Test(expected = MessageStoreException.class)
    public void storeFailed() {
        when(messageDao.store(any(), any(), any())).thenReturn(false);
        service.storeMessage("test", "test", "test");

        verify(messageDao, times(1)).store(any(), any(), any());
    }

    @Test
    public void sendSuccess() {
        List<MessageToSend> value = List.of(new MessageToSend(), new MessageToSend());
        when(messageDao.getUnsentMessages()).thenReturn(value);

        service.send();

        verify(messageDao, times(1)).markAsSent(value);
    }

    @Test
    public void sendException() throws Exception {
        List<MessageToSend> value = List.of(new MessageToSend(), new MessageToSend(), new MessageToSend());
        when(messageDao.getUnsentMessages()).thenReturn(value);

        Mockito.doThrow(MailNotSendException.class, RuntimeException.class)
                .doNothing()
                .when(mailSenderService).send(any(), any(), any(), any(), any());

        service.send();

        verify(messageDao, atLeastOnce()).getUnsentMessages();
        verify(messageDao, atLeastOnce()).markAsSent(List.of(value.get(2)));
    }

    static class Config {

        @Bean
        public MessageDao messageDao() {
            return mock(MessageDao.class);
        }

        @Bean
        public MailSenderService mailSenderService() {
            return mock(MailSenderService.class);
        }

        @Bean
        public ScheduledMailHandlerService service(MessageDao messageDao, MailSenderService mailSenderService) {
            return new ScheduledMailHandlerService(messageDao, mailSenderService);
        }

    }
}