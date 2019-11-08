package com.rbkmoney.dudoser.service;

import com.rbkmoney.damsel.payment_processing.Invoice;

public interface InvoicingService {

    Invoice get(String invoiceId, Long sequenceId);

}
