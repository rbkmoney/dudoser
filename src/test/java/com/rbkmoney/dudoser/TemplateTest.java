package com.rbkmoney.dudoser;

import com.rbkmoney.dudoser.dao.model.Content;
import com.rbkmoney.dudoser.dao.model.PaymentPayer;
import com.rbkmoney.dudoser.service.TemplateService;
import com.rbkmoney.dudoser.utils.Converter;
import com.rbkmoney.geck.common.util.TypeUtil;
import com.rbkmoney.testcontainers.annotations.postgresql.PostgresqlTestcontainerSingleton;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@PostgresqlTestcontainerSingleton
public class TemplateTest {

    @Autowired
    private TemplateService templateService;

    @Test
    public void cebTemplateTest() {
        PaymentPayer paymentPayer = buildPaymentPayer();
        Content content = new Content("test", TestData.kebMetadata());
        paymentPayer.setInvoiceMetadata(content);

        Map<String, Object> model = new HashMap<>();
        model.put("paymentPayer", paymentPayer);
        model.put("formattedAmount",
                Converter.getFormattedAmount(paymentPayer.getAmount(), paymentPayer.getCurrency()));
        String filledContent = templateService.getFilledContent(TestData.kebTemplate(), model);
        assertTrue(filledContent.contains("https://keb.test"));
        assertTrue(filledContent.contains("10000.00 RUB") || filledContent.contains("10000,00 RUB"));
        assertTrue(filledContent.contains("10.00 RUB") || filledContent.contains("10,00 RUB"));
        assertTrue(filledContent.contains("1.11 RUB") || filledContent.contains("1,11 RUB"));
    }

    @Test
    public void cebTemplateEmptyTest() {
        PaymentPayer paymentPayer = buildPaymentPayer();
        Content content = new Content("test", "".getBytes());
        paymentPayer.setInvoiceMetadata(content);

        Map<String, Object> model = new HashMap<>();
        model.put("paymentPayer", paymentPayer);
        model.put("formattedAmount",
                Converter.getFormattedAmount(paymentPayer.getAmount(), paymentPayer.getCurrency()));
        String filledContent = templateService.getFilledContent(TestData.kebTemplate(), model);
        assertTrue(filledContent.contains("https://keb.test"));
        assertTrue(filledContent.contains("1.11 RUB") || filledContent.contains("1,11 RUB"));
    }

    private PaymentPayer buildPaymentPayer() {
        PaymentPayer paymentPayer = PaymentPayer.builder().build();
        paymentPayer.setCardType("visa");
        paymentPayer.setCardMaskPan("5555");
        paymentPayer.setCurrency("RUB");
        paymentPayer.setAmount(Converter.longToBigDecimal(111L));
        paymentPayer.setInvoiceId("invoiceId");
        paymentPayer.setPaymentId("paymentId");
        paymentPayer.setDate(TypeUtil.stringToLocalDateTime("2016-10-26T20:12:47.983390Z"));
        paymentPayer.setToReceiver("i.ars@rbk.com");
        paymentPayer.setShopUrl("https://keb.test");

        return paymentPayer;
    }

}
