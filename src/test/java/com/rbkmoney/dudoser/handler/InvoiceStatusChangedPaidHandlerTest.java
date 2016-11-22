package com.rbkmoney.dudoser.handler;


import com.rbkmoney.damsel.domain.*;
import com.rbkmoney.damsel.event_stock.SourceEvent;
import com.rbkmoney.damsel.event_stock.StockEvent;
import com.rbkmoney.damsel.payment_processing.*;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@Ignore("Integration test")
public class InvoiceStatusChangedPaidHandlerTest {

    private final static long EVENT_ID = 1L;
    @Value("${test.mail.to}")
    private String E_MAIL;
    private final static String PHONE = "1234567890";

    @Autowired
    InvoiceStatusChangedPaidHandler handler;

    @Test
    public void testHandle() {
        StockEvent stockEvent = getStockEvent();
        handler.handle(stockEvent);
    }

    public StockEvent getStockEvent() {
        StockEvent stockEvent = new StockEvent();
        stockEvent.setSourceEvent(getSourceEvent());
        return stockEvent;
    }

    public SourceEvent getSourceEvent() {
        SourceEvent sourceEvent = new SourceEvent();
        sourceEvent.setProcessingEvent(getEvent());
        return sourceEvent;
    }

    public Event getEvent() {
        Event event = new Event();
        event.setCreatedAt("2016-12-12T12:12:12Z");
        event.setId(EVENT_ID);
        event.setSequence(1);
        event.setPayload(getEventPayload());
        event.setSource(getEventSource());
        return event;
    }

    public EventPayload getEventPayload() {
        EventPayload eventPayload = new EventPayload();
        eventPayload.setInvoiceEvent(getInvoiceEvent());
        return eventPayload;
    }

    public EventSource getEventSource() {
        EventSource eventSource = new EventSource();
        eventSource.setInvoice(generateRandomInvoiceId());
        return eventSource;
    }

    public InvoiceEvent getInvoiceEvent() {
        InvoiceEvent invoiceEvent = new InvoiceEvent();
        invoiceEvent.setInvoiceStatusChanged(getInvoiceStatusChanged());
        invoiceEvent.setInvoicePaymentEvent(getInvoicePaymentEvent());
        return invoiceEvent;
    }

    public InvoicePaymentEvent getInvoicePaymentEvent() {
        InvoicePaymentEvent invoicePaymentEvent = new InvoicePaymentEvent();
        invoicePaymentEvent.setInvoicePaymentStarted(getInvoicePaymentStarted());
        return invoicePaymentEvent;
    }

    public InvoicePaymentStarted getInvoicePaymentStarted() {
        InvoicePaymentStarted invoicePaymentStarted = new InvoicePaymentStarted();
        invoicePaymentStarted.setPayment(getInvoicePayment());
        return invoicePaymentStarted;
    }

    public InvoicePayment getInvoicePayment() {
        InvoicePayment invoicePayment = new InvoicePayment();
        invoicePayment.setPayer(getPayer());
        invoicePayment.setCost(getCash());
        invoicePayment.setCreatedAt("2016-12-12T12:12:12Z");
        return invoicePayment;
    }

    public Cash getCash() {
        Cash cash = new Cash();
        cash.setAmount(1000L);
        cash.setCurrency(getCurrency());
        return cash;
    }

    public Currency getCurrency() {
        Currency currency = new Currency();
        currency.setSymbolicCode("RUB");
        currency.setNumericCode((short) 643);
        currency.setExponent((short) 0);
        currency.setName("Rubles");
        return currency;
    }

    public Payer getPayer() {
        Payer payer = new Payer();
        payer.setSession("session");
        payer.setPaymentTool(getPaymentTool());
        payer.setContactInfo(getContactInfo());
        payer.setClientInfo(getClientInfo());
        return payer;
    }

    public ClientInfo getClientInfo() {
        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setFingerprint("fingerprint");
        clientInfo.setIpAddress("127.0.0.1");
        return clientInfo;
    }

    public ContactInfo getContactInfo() {
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setEmail(E_MAIL);
        contactInfo.setPhoneNumber(PHONE);
        return contactInfo;
    }

    public PaymentTool getPaymentTool() {
        PaymentTool paymentTool = new PaymentTool();
        paymentTool.setBankCard(getBankCard());
        return paymentTool;
    }

    public BankCard getBankCard() {
        BankCard bankCard = new BankCard();
        bankCard.setPaymentSystem(BankCardPaymentSystem.mastercard);
        bankCard.setMaskedPan("1234****1234");
        bankCard.setToken("token");
        bankCard.setBin("bin");
        return bankCard;
    }

    public InvoiceStatusChanged getInvoiceStatusChanged() {
        InvoiceStatusChanged invoiceStatusChanged = new InvoiceStatusChanged();
        InvoiceStatus invoiceStatus = new InvoiceStatus();
        InvoicePaid invoicePaid = new InvoicePaid();
        invoiceStatus.setPaid(invoicePaid);
        invoiceStatusChanged.setStatus(invoiceStatus);
        return invoiceStatusChanged;
    }


    public static String generateRandomInvoiceId() {
        String str = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ0123456789";
        String shuffle = shuffle(str);
        return shuffle.substring(0, 9);
    }

    public static String shuffle(String text) {
        char[] characters = text.toCharArray();
        for (int i = 0; i < characters.length; i++) {
            int randomIndex = (int) (Math.random() * characters.length);
            char temp = characters[i];
            characters[i] = characters[randomIndex];
            characters[randomIndex] = temp;
        }
        return new String(characters);
    }

}
