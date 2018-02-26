package com.rbkmoney.dudoser.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NestedRuntimeException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Optional;

import static com.rbkmoney.dudoser.dao.PaymentType.*;


@Component
public class PaymentPayerDaoImpl extends NamedParameterJdbcDaoSupport implements PaymentPayerDao {
    Logger log = LoggerFactory.getLogger(this.getClass());

    public PaymentPayerDaoImpl(DataSource dataSource) {
        setDataSource(dataSource);
    }

    @Override
    public boolean addPayment(final PaymentPayer payment) {
        final String sql = "INSERT INTO dudos.payment_payer(invoice_id, party_id, shop_id, shop_url, payment_id, amount, currency, card_type, card_mask_pan, date, to_receiver, type) " +
                "VALUES (:invoice_id, :party_id, :shop_id, :shop_url, :payment_id, :amount, :currency, :card_type, :card_mask_pan, :date, :to_receiver, CAST(:type AS dudos.payment_type))";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("invoice_id", payment.getInvoiceId())
                .addValue("party_id", payment.getPartyId())
                .addValue("shop_id", payment.getShopId())
                .addValue("shop_url", payment.getShopUrl())
                .addValue("payment_id", payment.getPaymentId())
                .addValue("amount", payment.getAmount())
                .addValue("currency", payment.getCurrency())
                .addValue("card_type", payment.getCardType())
                .addValue("card_mask_pan", payment.getCardMaskPan())
                .addValue("date", payment.getDate())
                .addValue("to_receiver", payment.getToReceiver())
                .addValue("type", PAYMENT.name());
        try {
            int updateCount = getNamedParameterJdbcTemplate().update(sql, params);
            if (updateCount != 1) {
                return false;
            }
        } catch (NestedRuntimeException e) {
            throw new DaoException("PaymentPayerDaoImpl.addPayment error with invoiceId "+ payment.getInvoiceId(), e);
        }
        log.debug("Payment {}.{} added to table", payment.getInvoiceId(), payment.getPaymentId());
        return true;
    }

    @Override
    public boolean addInvoice(String invoiceId, String partyId, String shopId, String shopUrl) {
        final String sql = "INSERT INTO dudos.payment_payer(invoice_id, party_id, shop_id, shop_url, type) " +
                "VALUES (:invoice_id, :party_id, :shop_id, :shop_url, CAST(:type AS dudos.payment_type))";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("invoice_id", invoiceId)
                .addValue("party_id", partyId)
                .addValue("shop_id", shopId)
                .addValue("shop_url", shopUrl)
                .addValue("type", INVOICE.name());
        try {
            int updateCount = getNamedParameterJdbcTemplate().update(sql, params);
            if (updateCount != 1) {
                return false;
            }
        } catch (NestedRuntimeException e) {
            throw new DaoException("PaymentPayerDaoImpl.addInvoice error with invoiceId "+ invoiceId, e);
        }
        log.debug("Payment info with invoiceId {} added to table", invoiceId);
        return true;
    }

    @Override
    public boolean addRefund(PaymentPayer refund) {
        final String sql = "INSERT INTO dudos.payment_payer(invoice_id, party_id, shop_id, shop_url, payment_id, refund_id, amount, currency, card_type, card_mask_pan, date, to_receiver, type) " +
                "VALUES (:invoice_id, :party_id, :shop_id, :shop_url, :payment_id, :refund_id, :amount, :currency, :card_type, :card_mask_pan, :date, :to_receiver, CAST(:type AS dudos.payment_type))";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("invoice_id", refund.getInvoiceId())
                .addValue("party_id", refund.getPartyId())
                .addValue("shop_id", refund.getShopId())
                .addValue("shop_url", refund.getShopUrl())
                .addValue("payment_id", refund.getPaymentId())
                .addValue("refund_id", refund.getRefundId())
                .addValue("amount", refund.getAmount())
                .addValue("currency", refund.getCurrency())
                .addValue("card_type", refund.getCardType())
                .addValue("card_mask_pan", refund.getCardMaskPan())
                .addValue("date", refund.getDate())
                .addValue("to_receiver", refund.getToReceiver())
                .addValue("type", REFUND.name());
        try {
            int updateCount = getNamedParameterJdbcTemplate().update(sql, params);
            if (updateCount != 1) {
                return false;
            }
        } catch (NestedRuntimeException e) {
            throw new DaoException("PaymentPayerDaoImpl.addRefund error with invoiceId "+ refund.getInvoiceId(), e);
        }
        log.debug("Refund with invoiceId {} added to table", refund.getInvoiceId());
        return true;
    }

    @Override
    public Optional<PaymentPayer> getPayment(String invoiceId, String paymentId) {
        final String sql = "SELECT * FROM dudos.payment_payer WHERE invoice_id =:invoice_id AND payment_id=:payment_id AND type=CAST(:type AS dudos.payment_type) ORDER BY ID DESC LIMIT 1";
        MapSqlParameterSource params = new MapSqlParameterSource("invoice_id", invoiceId)
                .addValue("payment_id", paymentId)
                .addValue("type", PAYMENT.name());
        PaymentPayer paymentPayer = null;
        try {
            paymentPayer = (PaymentPayer) getNamedParameterJdbcTemplate()
                    .queryForObject(sql, params, new BeanPropertyRowMapper(PaymentPayer.class));
        } catch (EmptyResultDataAccessException e) {
            //do nothing
        } catch (NestedRuntimeException e) {
            throw new DaoException("PaymentPayerDaoImpl.getPayment error with id " + invoiceId + "." + paymentId, e);
        }
        return Optional.ofNullable(paymentPayer);
    }

    @Override
    public Optional<PaymentPayer> getInvoice(String invoiceId) {
        final String sql = "SELECT * FROM dudos.payment_payer WHERE invoice_id =:invoice_id AND payment_id=:payment_id AND type=CAST(:type AS dudos.payment_type) ORDER BY ID DESC LIMIT 1";
        MapSqlParameterSource params = new MapSqlParameterSource("invoice_id", invoiceId)
                .addValue("type", INVOICE.name());
        PaymentPayer paymentPayer = null;
        try {
            paymentPayer = (PaymentPayer) getNamedParameterJdbcTemplate()
                    .queryForObject(sql, params, new BeanPropertyRowMapper(PaymentPayer.class));
        } catch (EmptyResultDataAccessException e) {
            //do nothing
        } catch (NestedRuntimeException e) {
            throw new DaoException("PaymentPayerDaoImpl.getInvoice error with id " + invoiceId, e);
        }
        return Optional.ofNullable(paymentPayer);
    }

    @Override
    public Optional<PaymentPayer> getRefund(String invoiceId, String paymentId, String refundId) {
        final String sql = "SELECT * FROM dudos.payment_payer WHERE invoice_id =:invoice_id AND payment_id=:payment_id AND refund_id=:refund_id AND type=CAST(:type AS dudos.payment_type) ORDER BY ID DESC LIMIT 1";
        MapSqlParameterSource params = new MapSqlParameterSource("invoice_id", invoiceId)
                .addValue("payment_id", paymentId)
                .addValue("refund_id", refundId)
                .addValue("type", REFUND.name());
        PaymentPayer paymentPayer = null;
        try {
            paymentPayer = (PaymentPayer) getNamedParameterJdbcTemplate()
                    .queryForObject(sql, params, new BeanPropertyRowMapper(PaymentPayer.class));
        } catch (EmptyResultDataAccessException e) {
            //do nothing
        } catch (NestedRuntimeException e) {
            throw new DaoException("PaymentPayerDaoImpl.getRefund error with id " + invoiceId + "." + paymentId + "." + refundId, e);
        }
        return Optional.ofNullable(paymentPayer);
    }
}
