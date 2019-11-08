package com.rbkmoney.dudoser.service;

import com.rbkmoney.dudoser.dao.MessageDao;
import com.rbkmoney.dudoser.dao.model.MessageToSend;
import com.rbkmoney.dudoser.exception.MailNotSendException;
import com.rbkmoney.dudoser.exception.MessageStoreException;
import com.sun.mail.smtp.SMTPAddressFailedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.mail.MailSendException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduledMailHandlerService {

    @Value("${notification.payment.paid.from}")
    private String from;

    @Value("${message.store.days}")
    private Integer storeDays;

    @Value("${message.fail.minutes}")
    private Integer failTime;

    private final MessageDao messageDao;
    private final MailSenderService mailSenderService;
    private final ExecutorService mailSendingExecutorService;
    private final TransactionTemplate transactionTemplate;

    public void storeMessage(String receiver, String subject, String text) {
        if (!messageDao.store(receiver, subject, text)) {
            throw new MessageStoreException("Can't save message to db");
        }
    }

    @Scheduled(fixedRateString = "${message.schedule.send}")
    public void send() {
        transactionTemplate.execute(status -> {
            sendInternal();
            return null;
        });
    }

    private void sendInternal() {
        List<MessageToSend> unsentMessages = messageDao.getUnsentMessages();
        log.info("Mail sending started... Messages to send: {}", unsentMessages.size());

        Map<MessageToSend, Boolean> messageSendResults = unsentMessages
                .stream()
                .map(messageToSend -> Map.entry(messageToSend,
                        CompletableFuture.supplyAsync(() -> sendSucceeded(messageToSend), mailSendingExecutorService)))
                .collect(Collectors.toMap(Map.Entry::getKey, o -> o.getValue().join()));

        List<MessageToSend> sentMessages = messageSendResults.entrySet()
                .stream()
                .filter(Map.Entry::getValue)
                .map(Map.Entry::getKey)
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
            log.info("Message with subject {} was sent to {}", messageToSend.getSubject(), messageToSend.getReceiver());
            return true;
        } catch (MailNotSendException e) {
            Throwable mostSpecificCause = NestedExceptionUtils.getMostSpecificCause(e);
            if (mostSpecificCause instanceof MailSendException
                    && ((MailSendException) mostSpecificCause)
                        .getFailedMessages()
                        .values()
                        .stream()
                        .anyMatch(err ->
                                NestedExceptionUtils.getMostSpecificCause(err) instanceof SMTPAddressFailedException)) {
                log.info("Can't find email address, receiver: {}", messageToSend.getReceiver());
                return true; //we don't need to retry it
            }
            log.error("Mail wasn't send, subject: {}, receiver: {}",
                    messageToSend.getSubject(), messageToSend.getReceiver(), e);
            return false;
        } catch (Exception e) {
            log.error("Unexpected exception while sending message, subject: {}, receiver: {}",
                    messageToSend.getSubject(), messageToSend.getReceiver(), e);
            return false;
        }
    }

    @Scheduled(fixedRateString = "${message.schedule.clear.sent}")
    public void clearSentMessages() {
        log.info("Message clearing started.");
        messageDao.deleteMessages(Instant.now().minus(storeDays, ChronoUnit.DAYS), true);
    }

    @Scheduled(fixedRateString = "${message.schedule.clear.failed}")
    public void clearFailedMessages() {
        log.info("Message clearing started.");
        messageDao.deleteMessages(Instant.now().minus(failTime, ChronoUnit.MINUTES), false);
    }

}
