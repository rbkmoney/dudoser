package com.rbkmoney.dudoser.handler.poller;

import com.rbkmoney.damsel.domain.Invoice;
import com.rbkmoney.damsel.payment_processing.InvoiceChange;
import com.rbkmoney.dudoser.dao.DaoException;
import com.rbkmoney.dudoser.dao.PaymentPayerDaoImpl;
import com.rbkmoney.dudoser.dao.model.Content;
import com.rbkmoney.dudoser.handler.ChangeType;
import com.rbkmoney.dudoser.service.PartyManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class InvoiceCreatedHandler implements PollingEventHandler {

    private final PaymentPayerDaoImpl paymentDao;
    private final PartyManagementService partyManagementService;

    @Override
    public void handle(InvoiceChange ic, String sourceId) throws DaoException {
        Invoice invoice = ic.getInvoiceCreated().getInvoice();
        String shopUrl;
        if (invoice.isSetPartyRevision()) {
            shopUrl = partyManagementService.getShopUrl(invoice.getOwnerId(), invoice.getShopId(), invoice.getPartyRevision());
        } else {
            shopUrl = partyManagementService.getShopUrl(invoice.getOwnerId(), invoice.getShopId(), invoice.getCreatedAt());
        }

        log.info("Start creating invoice with id {}", invoice.getId());
        Content content = null;
        if (invoice.isSetContext()) {
            content = new Content(invoice.getContext().getType(), invoice.getContext().getData());
        }
        paymentDao.addInvoice(invoice.getId(), invoice.getOwnerId(), invoice.getShopId(), shopUrl, content);
        log.info("End creating invoice with id {}", invoice.getId());
    }

    @Override
    public ChangeType getChangeType() {
        return ChangeType.INVOICE_CREATED;
    }
}
