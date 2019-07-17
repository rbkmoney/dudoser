package com.rbkmoney.dudoser.service;

import com.rbkmoney.dudoser.dao.MessageDao;
import com.rbkmoney.dudoser.dao.model.MessageToSend;
import com.rbkmoney.dudoser.exception.MailNotSendException;
import com.rbkmoney.dudoser.exception.MessageStoreException;
import com.sun.mail.smtp.SMTPAddressFailedException;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.hamcrest.MockitoHamcrest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.SendFailedException;
import java.util.List;
import java.util.concurrent.Executors;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ScheduledMailHandlerServiceTest.Config.class)
@TestPropertySource(properties = {
        "message.store.days=1",
        "notification.payment.paid.from=test",
        "message.schedule.send=2000",
        "message.schedule.clear.sent=2000",
        "message.schedule.clear.failed=2000",
        "message.fail.minutes=5"
})
@Ignore
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
        MessageToSend msg1 = new MessageToSend();
        msg1.setSubject("1");
        MessageToSend msg2 = new MessageToSend();
        msg2.setSubject("2");
        List<MessageToSend> value = List.of(msg1, msg2);
        when(messageDao.getUnsentMessages()).thenReturn(value);

        service.send();

        verify(messageDao, times(1))
                .markAsSent((List<MessageToSend>) MockitoHamcrest.argThat(containsInAnyOrder(msg1, msg2)));
    }

    @Test
    public void sendException() throws Exception {
        MessageToSend msg1 = new MessageToSend();
        msg1.setSubject("1");
        MessageToSend msg2 = new MessageToSend();
        msg2.setSubject("2");
        MessageToSend msg3 = new MessageToSend();
        msg3.setSubject("3");
        MessageToSend msg4 = new MessageToSend();
        msg4.setSubject("4");
        List<MessageToSend> value = List.of(msg1, msg2, msg3, msg4);
        when(messageDao.getUnsentMessages()).thenReturn(value);

        Mockito.doThrow(new MailNotSendException(
                        "test", new SendFailedException(
                        "test", new SMTPAddressFailedException(null, null, 0, "err"))))
                .doThrow(RuntimeException.class)
                .doThrow(MailNotSendException.class)
                .doNothing()
                .when(mailSenderService).send(any(), any(), any(), any(), any());

        service.send();

        verify(messageDao, atLeastOnce()).getUnsentMessages();
        verify(messageDao, atLeastOnce()).markAsSent((List<MessageToSend>) MockitoHamcrest.argThat(containsInAnyOrder(value.get(0), value.get(3))));
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
            return new ScheduledMailHandlerService(messageDao, mailSenderService, Executors.newSingleThreadExecutor());
        }

    }
}