package com.rbkmoney.dudoser.service.impl;

import com.rbkmoney.damsel.payment_processing.*;
import com.rbkmoney.dudoser.exception.InvoicingClientException;
import com.rbkmoney.dudoser.exception.NotFoundException;
import com.rbkmoney.dudoser.service.InvoicingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvoicingServiceImpl implements InvoicingService {

    private final InvoicingSrv.Iface invoicingClient;

    private final UserInfo userInfo;

    @Override
    public Invoice get(String invoiceId, Long sequenceId) {
        try {
            log.info("Trying to get invoice, invoiceId='{}'", invoiceId);
            Invoice invoice = invoicingClient.get(userInfo, invoiceId, getEventRange(sequenceId));
            log.info("Shop has been found, invoiceId='{}'", invoiceId);
            return invoice;
        } catch (InvoiceNotFound invoiceNotFound) {
            throw new NotFoundException("Invoice not found", invoiceNotFound);
        } catch (TException e) {
            throw new InvoicingClientException("Error receiving the invoice", e);
        }
    }

    private EventRange getEventRange(Long sequenceId) {
        EventRange eventRange = new EventRange();
        eventRange.setLimit(sequenceId.intValue());
        return eventRange;
    }
}
