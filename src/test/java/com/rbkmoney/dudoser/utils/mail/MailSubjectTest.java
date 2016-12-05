package com.rbkmoney.dudoser.utils.mail;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MailSubjectTest {

    @Test
    public void testSubjectPattern() {
        String invoiceId = "InvoiceId123";
        String date = "12-12-2016";
        String amountWithCurrency = "111.11 RUB";

        assertEquals("Сформирован счет № " + invoiceId + " от " + date + " на сумму " + amountWithCurrency,
                String.format(MailSubject.FORMED_THROUGH.pattern, invoiceId, date, amountWithCurrency)
        );

        assertEquals("Счет № " + invoiceId + " от " + date + " на сумму " + amountWithCurrency + ". Успешно оплачен",
                String.format(MailSubject.PAYMENT_PAID.pattern, invoiceId, date, amountWithCurrency)
        );
    }
}
