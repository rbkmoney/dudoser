package com.rbkmoney.dudoser.utils.mail;

public enum MailSubject {

    /**
     * Fields: InvoiceId, Date, Amount
     * Сформирован счет № Dud124 от 12-12-2016 на сумму 100.12 RUB
     * <p>
     * Example:
     * String.format(MailSubject.FORMED_THROUGH.pattern, invoiceId, date, amount);
     */
    FORMED_THROUGH("Сформирован счет № %s от %s на сумму %s"),

    /**
     * Fields: InvoiceId, Date, Amount
     * Счет № Dud124 от 12-12-2016 на сумму 100.12 RUB. Успешно оплачен
     * <p>
     * Example:
     * String.format(MailSubject.PAYMENT_PAID.pattern, invoiceId, date, amount);
     */
    PAYMENT_PAID("Счет № %s от %s на сумму %s. Успешно оплачен");

    MailSubject(String pattern) {
        this.pattern = pattern;
    }

    public final String pattern;

    @Override
    public String toString() {
        return pattern;
    }

}
