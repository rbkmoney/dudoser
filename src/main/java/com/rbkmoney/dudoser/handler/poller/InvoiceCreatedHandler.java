package com.rbkmoney.dudoser.handler.poller;

import com.rbkmoney.damsel.domain.Invoice;
import com.rbkmoney.damsel.event_stock.StockEvent;
import com.rbkmoney.damsel.payment_processing.InvoiceChange;
import com.rbkmoney.dudoser.dao.DaoException;
import com.rbkmoney.dudoser.dao.PaymentPayerDaoImpl;
import com.rbkmoney.dudoser.handler.ChangeType;
import com.rbkmoney.dudoser.service.PartyManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InvoiceCreatedHandler implements PollingEventHandler {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PaymentPayerDaoImpl paymentDao;

    @Autowired
    PartyManagementService partyManagementService;

    @Override
    public void handle(InvoiceChange ic, StockEvent value) throws DaoException {
        Invoice invoice = ic.getInvoiceCreated().getInvoice();
        String shopUrl = partyManagementService.getShopUrl(invoice.getOwnerId(), invoice.getShopId(), invoice.getCreatedAt());
        log.info("Start creating invoice with id {}", invoice.getId());
        paymentDao.addInvoice(invoice.getId(), invoice.getOwnerId(), invoice.getShopId(), shopUrl);
        log.info("End creating invoice with id {}", invoice.getId());
    }

    @Override
    public ChangeType getChangeType() {
        return ChangeType.INVOICE_CREATED;
    }
}
