package com.rbkmoney.dudoser.dao;

import com.rbkmoney.dudoser.dao.model.PaymentPayer;
import com.rbkmoney.geck.common.util.TypeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedRuntimeException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Optional;

import static com.rbkmoney.dudoser.dao.PaymentType.*;


@Slf4j
@Component
public class PaymentPayerDaoImpl extends NamedParameterJdbcDaoSupport implements PaymentPayerDao {

    public PaymentPayerDaoImpl(DataSource dataSource) {
        setDataSource(dataSource);
    }

    @Override
    public void addPayment(final PaymentPayer payment) {
        final String sql = "INSERT INTO dudos.payment_payer(invoice_id, party_id, shop_id, shop_url, payment_id, amount, currency, card_type, card_mask_pan, date, to_receiver, type) " +
                "VALUES (:invoice_id, :party_id, :shop_id, :shop_url, :payment_id, :amount, :currency, :card_type, :card_mask_pan, :date, :to_receiver, CAST(:type AS dudos.payment_type)) " +
                "ON CONFLICT (invoice_id, payment_id) WHERE payment_id is not null and refund_id is null DO NOTHING";
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
            getNamedParameterJdbcTemplate().update(sql, params);
        } catch (NestedRuntimeException e) {
            log.warn("Couldn't save payment info: {}.{}", payment.getInvoiceId(), payment.getPaymentId());
            throw new DaoException("PaymentPayerDaoImpl.addPayment error with invoiceId "+ payment.getInvoiceId(), e);
        }
        log.info("Payment {}.{} added to table", payment.getInvoiceId(), payment.getPaymentId());
    }

    @Override
    public void addInvoice(String invoiceId, String partyId, String shopId, String shopUrl) {
        final String sql = "INSERT INTO dudos.payment_payer(invoice_id, party_id, shop_id, shop_url, type) " +
                "VALUES (:invoice_id, :party_id, :shop_id, :shop_url, CAST(:type AS dudos.payment_type)) " +
                "ON CONFLICT (invoice_id) WHERE payment_id is null and refund_id is null DO NOTHING";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("invoice_id", invoiceId)
                .addValue("party_id", partyId)
                .addValue("shop_id", shopId)
                .addValue("shop_url", shopUrl)
                .addValue("type", INVOICE.name());
        try {
            getNamedParameterJdbcTemplate().update(sql, params);
        } catch (NestedRuntimeException e) {
            log.warn("Couldn't save invoice info: {}", invoiceId);
            throw new DaoException("PaymentPayerDaoImpl.addInvoice error with invoiceId "+ invoiceId, e);
        }
        log.info("Payment info with invoiceId {} added to table", invoiceId);
    }

    @Override
    public void addRefund(PaymentPayer refund) {
        final String sql = "INSERT INTO dudos.payment_payer(invoice_id, party_id, shop_id, shop_url, payment_id, refund_id, amount, refund_amount, currency, card_type, card_mask_pan, date, to_receiver, type) " +
                "VALUES (:invoice_id, :party_id, :shop_id, :shop_url, :payment_id, :refund_id, :amount, :refund_amount, :currency, :card_type, :card_mask_pan, :date, :to_receiver, CAST(:type AS dudos.payment_type)) " +
                "ON CONFLICT (invoice_id, payment_id, refund_id) WHERE payment_id is not null and refund_id is not null DO NOTHING";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("invoice_id", refund.getInvoiceId())
                .addValue("party_id", refund.getPartyId())
                .addValue("shop_id", refund.getShopId())
                .addValue("shop_url", refund.getShopUrl())
                .addValue("payment_id", refund.getPaymentId())
                .addValue("refund_id", refund.getRefundId())
                .addValue("amount", refund.getAmount())
                .addValue("refund_amount", refund.getRefundAmount())
                .addValue("currency", refund.getCurrency())
                .addValue("card_type", refund.getCardType())
                .addValue("card_mask_pan", refund.getCardMaskPan())
                .addValue("date", refund.getDate())
                .addValue("to_receiver", refund.getToReceiver())
                .addValue("type", REFUND.name());
        try {
            getNamedParameterJdbcTemplate().update(sql, params);
        } catch (NestedRuntimeException e) {
            log.warn("Couldn't save refund info: {}.{}.{}", refund.getInvoiceId(), refund.getPaymentId(), refund.getRefundId());
            throw new DaoException("PaymentPayerDaoImpl.addRefund error with invoiceId "+ refund.getInvoiceId(), e);
        }
        log.info("Refund with invoiceId {} added to table", refund.getInvoiceId());
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
        final String sql = "SELECT * FROM dudos.payment_payer WHERE invoice_id =:invoice_id AND type=CAST(:type AS dudos.payment_type) ORDER BY ID DESC LIMIT 1";
        MapSqlParameterSource params = new MapSqlParameterSource("invoice_id", invoiceId)
                .addValue("type", INVOICE.name());
        PaymentPayer paymentPayer = null;
        try {
            paymentPayer = getNamedParameterJdbcTemplate()
                    .queryForObject(sql, params, (resultSet, i) -> {
                        PaymentPayer pp = new PaymentPayer();
                        pp.setAmount(resultSet.getBigDecimal("amount"));
                        pp.setRefundAmount(resultSet.getBigDecimal("refund_amount"));
                        pp.setCurrency(resultSet.getString("currency"));
                        pp.setCardType(resultSet.getString("card_type"));
                        pp.setCardMaskPan(resultSet.getString("card_mask_pan"));
                        pp.setInvoiceId(resultSet.getString("invoice_id"));
                        pp.setPaymentId(resultSet.getString("payment_id"));
                        pp.setRefundId(resultSet.getString("refund_id"));
                        pp.setPartyId(resultSet.getString("party_id"));
                        pp.setShopId(resultSet.getString("shop_id"));
                        pp.setShopUrl(resultSet.getString("shop_url"));
                        String sDate = resultSet.getString("date");
                        if (sDate != null) {
                            pp.setDate(TypeUtil.stringToLocalDateTime(sDate));
                        }
                        pp.setToReceiver(resultSet.getString("to_receiver"));
                        return pp;
                    });
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
            throw new DaoException("PaymentPayerDaoImpl.getRefund error with id " + invoiceId + "." + paymentId, e);
        }
        return Optional.ofNullable(paymentPayer);
    }
}
