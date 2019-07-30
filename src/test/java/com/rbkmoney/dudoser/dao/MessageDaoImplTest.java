package com.rbkmoney.dudoser.dao;

import com.rbkmoney.dudoser.AbstractIntegrationTest;
import com.rbkmoney.dudoser.dao.model.MessageToSend;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        //schedulers in context ):
        "message.schedule.send=20000",
        "message.schedule.clear.sent=20000",
        "message.schedule.clear.failed=20000",
})
public class MessageDaoImplTest extends AbstractIntegrationTest {

    @Autowired
    MessageDao messageDao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    TransactionTemplate transactionTemplate;

    @Test
    @Transactional
    public void messageDaoTest() {
        messageDao.store("jenya@toxic.ru", "Today is a non-toxic day", "text!".repeat(255));
        messageDao.store("jenya@toxic.ru", "Today is a non-toxic day", "text!".repeat(255));
        messageDao.store("jenya@toxic.ru", "Today is a toxic day", "text!".repeat(255));

        List<MessageToSend> unsentMessages = messageDao.getUnsentMessages();
        Assert.assertEquals(2, unsentMessages.size()); // subject primary key test
        unsentMessages.forEach(message -> Assert.assertFalse(message.getSent()));

        messageDao.markAsSent(unsentMessages);
        unsentMessages = messageDao.getUnsentMessages();
        Assert.assertEquals(0, unsentMessages.size()); // test mark as sent

        messageDao.deleteMessages(Instant.now().plus(10, ChronoUnit.DAYS), true);
        List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT * FROM dudos.mailing_list");
        Assert.assertEquals(0, list.size()); //delete works
    }

    @Test
    @Transactional
    public void messageClearingTest() {
        messageDao.store("jenya@toxic.ru", "Today is a toxic day", "text!".repeat(255));
        List<MessageToSend> unsentMessages = messageDao.getUnsentMessages();
        Assert.assertEquals(1, unsentMessages.size()); // subject primary key test

        messageDao.deleteMessages(Instant.now().plus(1, ChronoUnit.MINUTES), false);

        unsentMessages = messageDao.getUnsentMessages();
        Assert.assertEquals(0, unsentMessages.size()); // deleted unsent messages

        List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT * FROM dudos.mailing_list");
        Assert.assertEquals(0, list.size()); //delete works
    }

    @Test
    @Transactional
    public void selectIsLimited() {
        for (int i = 0; i < 200; i++) {
            messageDao.store("jenya@toxic.ru", UUID.randomUUID().toString(), "text!".repeat(255));
        }
        List<MessageToSend> unsentMessages = messageDao.getUnsentMessages();
        Assert.assertEquals(100L, unsentMessages.size());
    }

    @Test
    public void selectIsLockedForUpdate() {
        for (int i = 0; i < 50; i++) {
            messageDao.store("jenya@toxic.ru", UUID.randomUUID().toString(), "text!".repeat(255));
        }
        new Thread(() -> transactionTemplate.execute(status -> {
            List<MessageToSend> unsentMessages = messageDao.getUnsentMessages();
            Assert.assertEquals(50L, unsentMessages.size());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        })).start();

        //waiting for thread init
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //checking locked rows
        transactionTemplate.execute(status -> {
            List<MessageToSend> unsentMessages = messageDao.getUnsentMessages();
            Assert.assertEquals(0L, unsentMessages.size());
            return null;
        });
    }

}