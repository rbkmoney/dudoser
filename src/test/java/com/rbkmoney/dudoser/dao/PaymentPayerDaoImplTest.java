package com.rbkmoney.dudoser.dao;

import com.rbkmoney.dudoser.utils.Converter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by inalarsanukaev on 06.12.16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PaymentPayerDaoImplTest {

    @Autowired
    PaymentPayerDao paymentPayerDao;
    @Test
    public void test() throws Exception {
        PaymentPayer paymentPayer = new PaymentPayer();
        paymentPayer.setCardType("visa");
        paymentPayer.setCardMaskPan("5555");
        paymentPayer.setCurrency("RUB");
        paymentPayer.setAmount(Converter.longToBigDecimal(111L));
        paymentPayer.setInvoiceId("eeeeeer");
        paymentPayer.setDate("2016-10-26T20:12:47.983390Z");
        paymentPayer.setToReceiver("i.ars@rbk.com");
        assertTrue(paymentPayerDao.add(paymentPayer));
        assertEquals(paymentPayerDao.getById("eeeeeer").get().getCardType(), "visa");
        assertTrue(paymentPayerDao.delete("eeeeeer"));
    }
}