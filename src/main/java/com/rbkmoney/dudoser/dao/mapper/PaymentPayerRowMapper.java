package com.rbkmoney.dudoser.dao.mapper;

import com.rbkmoney.dudoser.dao.model.Content;
import com.rbkmoney.dudoser.dao.model.PaymentPayer;
import com.rbkmoney.geck.common.util.TypeUtil;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

public class PaymentPayerRowMapper implements RowMapper<PaymentPayer> {

    @Override
    public PaymentPayer mapRow(ResultSet rs, int rowNum) throws SQLException {
        PaymentPayer paymentPayer = new PaymentPayer();
        paymentPayer.setAmount(rs.getBigDecimal("amount"));
        paymentPayer.setRefundAmount(rs.getBigDecimal("refund_amount"));
        paymentPayer.setCurrency(rs.getString("currency"));
        paymentPayer.setCardType(rs.getString("card_type"));
        paymentPayer.setCardMaskPan(rs.getString("card_mask_pan"));
        paymentPayer.setInvoiceId(rs.getString("invoice_id"));
        paymentPayer.setPaymentId(rs.getString("payment_id"));
        paymentPayer.setRefundId(rs.getString("refund_id"));
        paymentPayer.setPartyId(rs.getString("party_id"));
        paymentPayer.setShopId(rs.getString("shop_id"));
        paymentPayer.setShopUrl(rs.getString("shop_url"));
        Timestamp date = rs.getTimestamp("date");
        if (date != null) {
            paymentPayer.setDate(date.toLocalDateTime());
        }
        paymentPayer.setToReceiver(rs.getString("to_receiver"));

        Content content = new Content();
        content.setType(rs.getString("content_type"));
        content.setData(rs.getBytes("content_data"));
        paymentPayer.setMetadata(content);

        return paymentPayer;
    }

}
