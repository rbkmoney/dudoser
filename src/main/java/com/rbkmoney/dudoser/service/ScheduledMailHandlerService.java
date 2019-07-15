package com.rbkmoney.dudoser.service;

import com.rbkmoney.dudoser.dao.MessageDao;
import com.rbkmoney.dudoser.dao.model.MessageToSend;
import com.rbkmoney.dudoser.exception.MailNotSendException;
import com.rbkmoney.dudoser.exception.MessageStoreException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduledMailHandlerService {

    @Value("${notification.payment.paid.from}")
    private String from;

    @Value("${message.store.days}")
    private Integer storeDays;

    private final MessageDao messageDao;
    private final MailSenderService mailSenderService;

    public void storeMessage(String receiver, String subject, String text) {
        if (!messageDao.store(receiver, subject, text)) {
            throw new MessageStoreException("Can't save message to db");
        }
    }

    @Scheduled(fixedRateString = "${message.schedule.send}")
    public void send() {
        List<MessageToSend> unsentMessages = messageDao.getUnsentMessages();
        log.info("Mail sending started... Messages to send: {}", unsentMessages.size());
        List<MessageToSend> sentMessages = unsentMessages
                .parallelStream()
                .filter(this::sendSucceeded)
                .collect(Collectors.toList());

        messageDao.markAsSent(sentMessages);
        log.info("Sent {} messages", sentMessages.size());
    }

    private boolean sendSucceeded(MessageToSend messageToSend) {
        try {
            mailSenderService.send(from,
                    new String[]{messageToSend.getReceiver()},
                    messageToSend.getSubject(),
                    messageToSend.getBody(),
                    null);
            return true;
        } catch (MailNotSendException e) {
            log.error("Mail wasn't send, subject: {}, receiver: {}",
                    messageToSend.getSubject(), messageToSend.getReceiver(), e);
            return false;
        } catch (Exception e) {
            log.error("Unexpected exception while sending message, subject: {}, receiver: {}",
                    messageToSend.getSubject(), messageToSend.getReceiver(), e);
            return false;
        }
    }

    @Scheduled(fixedDelayString = "${message.schedule.clear}")
    public void clear() {
        log.info("Message clearing started.");
        messageDao.deleteSentMessages(Instant.now().minus(storeDays, ChronoUnit.DAYS));
    }

}
