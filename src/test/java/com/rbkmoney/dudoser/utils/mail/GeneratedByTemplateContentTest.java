package com.rbkmoney.dudoser.utils.mail;

import com.rbkmoney.dudoser.dao.EventTypeCode;
import com.rbkmoney.dudoser.dao.PaymentPayer;
import com.rbkmoney.dudoser.dao.TemplateDao;
import com.rbkmoney.dudoser.utils.Converter;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GeneratedByTemplateContentTest {
    @Autowired
    TemplateMailSenderUtils mailSenderUtils;
    @Autowired
    TemplateDao templateDao;
    @Value("${mail.username}")
    private String from;
    @Value("${test.mail.to}")
    private String to;

    @Test
    public void testMe() throws Exception {

        PaymentPayer paymentPayer = new PaymentPayer();
        paymentPayer.setCardType("visa");
        paymentPayer.setCardMaskPan("5555");
        paymentPayer.setCurrency("RUB");
        paymentPayer.setAmount(Converter.longToBigDecimal(111L));
        paymentPayer.setInvoiceId("de3dddQscG135Hgf");
        paymentPayer.setDate("2016-10-26T20:12:47.983390Z");
        paymentPayer.setToReceiver(to);

        Map<String, Object> model = new HashMap<>();
        model.put("paymentPayer", paymentPayer);

        String freeMarkerTemplateContent = templateDao.getTemplateBodyByMerchShopParams(EventTypeCode.PAYMENT_STARTED, "1", "1");
        mailSenderUtils.setFreeMarkerTemplateContent(freeMarkerTemplateContent);
        mailSenderUtils.setModel(model);

        String filledInvoice = mailSenderUtils.getFilledFreeMarkerTemplateContent().replaceAll("\r\n", "\n");
        String content = new String(Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource("payment_started.html").toURI())), "UTF-8").replaceAll("\r\n", "\n");
        assertEquals(filledInvoice, content);
    }
}
