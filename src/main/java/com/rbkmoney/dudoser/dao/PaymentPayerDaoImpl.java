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


@Component
public class PaymentPayerDaoImpl extends NamedParameterJdbcDaoSupport implements PaymentPayerDao {
    Logger log = LoggerFactory.getLogger(this.getClass());

    public PaymentPayerDaoImpl(DataSource dataSource) {
        setDataSource(dataSource);
    }

    @Override
    public Optional<PaymentPayer> getById(final String id) {
        final String sql = "SELECT * FROM dudos.payment_payer WHERE invoice_id =:invoice_id";
        MapSqlParameterSource params = new MapSqlParameterSource("invoice_id", id);
        PaymentPayer paymentPayer = null;
        try {
            paymentPayer = (PaymentPayer) getNamedParameterJdbcTemplate()
                    .queryForObject(sql, params, new BeanPropertyRowMapper(PaymentPayer.class));
        } catch (EmptyResultDataAccessException e) {
            //do nothing
        } catch (NestedRuntimeException e) {
            log.error("PaymentPayerDaoImpl.getById error with id {}", id, e);
            throw new DaoException(e);
        }
        return Optional.ofNullable(paymentPayer);
    }

    @Override
    public boolean add(final PaymentPayer paymentPayer) {
        if (getById(paymentPayer.getInvoiceId()).isPresent()) {
            log.warn("Payment info with invoiceId = {} already exists", paymentPayer.getInvoiceId());
            return false;
        }
        final String sql = "INSERT INTO dudos.payment_payer(invoice_id, amount, currency, card_type, card_mask_pan, date, to_receiver) " +
                "VALUES (:invoice_id, :amount, :currency, :card_type, :card_mask_pan, :date, :to_receiver)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("invoice_id", paymentPayer.getInvoiceId())
                .addValue("amount", paymentPayer.getAmount())
                .addValue("currency", paymentPayer.getCurrency())
                .addValue("card_type", paymentPayer.getCardType())
                .addValue("card_mask_pan", paymentPayer.getCardMaskPan())
                .addValue("date", paymentPayer.getDate())
                .addValue("to_receiver", paymentPayer.getToReceiver());
        try {
            int updateCount = getNamedParameterJdbcTemplate().update(sql, params);
            if (updateCount != 1) {
                return false;
            }
        } catch (NestedRuntimeException e) {
            log.error("PaymentPayerDaoImpl.add error with invoiceId {}", paymentPayer.getInvoiceId(), e);
            throw new DaoException(e);
        }
        log.debug("Payment info with invoiceId {} added to table", paymentPayer.getInvoiceId());
        return true;
    }

    @Override
    public boolean delete(final String id) {
        log.debug("Start deleting payment info with invoiceId = {}", id);
        final String sql = "DELETE FROM dudos.payment_payer where invoice_id=:invoice_id";
        try {
            int updateCount = getNamedParameterJdbcTemplate().update(sql, new MapSqlParameterSource("invoice_id", id));
            if (updateCount != 1) {
                return false;
            }
        } catch (NestedRuntimeException e) {
            log.error("PaymentPayerDaoImpl.delete error with id {}", id, e);
            throw new DaoException(e);
        }
        log.debug("Payment info with invoiceId {} deleted from table", id);
        return true;
    }
}