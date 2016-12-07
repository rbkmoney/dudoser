package com.rbkmoney.dudoser.dao;

import com.rbkmoney.dudoser.exception.UnknownException;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.*;
import java.util.Optional;

import static com.rbkmoney.dudoser.domain.Tables.JAVAOBJECT;


@Component
public class InMemoryPaymentPayerDao implements PaymentPayerDao {
    Logger log = LoggerFactory.getLogger(this.getClass());
    private DSLContext dslContext;

    public InMemoryPaymentPayerDao(DataSource dataSource) {
        dslContext = DSL.using(dataSource, SQLDialect.POSTGRES);
    }

    @Override
    public Optional<PaymentPayer> getById(final String id) {
        Record1<byte[]> record1 = dslContext.select(JAVAOBJECT.OBJECT).from(JAVAOBJECT).where(JAVAOBJECT.SOURCE_ID.eq(id)).fetchAny();
        PaymentPayer paymentPayer = null;
        if (record1 != null) {
            byte[] obj = record1.get(JAVAOBJECT.OBJECT);
            InputStream bis = new ByteArrayInputStream(obj);

            try {
                ObjectInputStream oin = new ObjectInputStream(bis);
                paymentPayer = (PaymentPayer) oin.readObject();
            } catch (Exception e) {
                log.error("Unknown exception; Couldn't read object from table", e);
                throw new UnknownException(e);
            }
        }
        boolean result = paymentPayer != null;
        log.info("Result of getting object with id {} from table: {}", id, result);
        return Optional.ofNullable(paymentPayer);
    }

    @Override
    public boolean add(final PaymentPayer paymentPayer) {
        if (getById(paymentPayer.getInvoiceId()).isPresent()) {
            return false;
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] bytes = null;
        try {
            ObjectOutput out = new ObjectOutputStream(bos);
            out.writeObject(paymentPayer);
            out.flush();
            bytes = bos.toByteArray();
        } catch (IOException e) {
            log.error("Unknown exception; Couldn't write object to table", e);
            throw new UnknownException(e);
        }

        boolean result = dslContext.insertInto(JAVAOBJECT, JAVAOBJECT.SOURCE_ID, JAVAOBJECT.OBJECT)
                .values(paymentPayer.getInvoiceId(), bytes).execute() == 1;

        log.info("Result of adding object with id {} to table: {}", paymentPayer.getInvoiceId(), result);
        return result;
    }

    @Override
    public boolean delete(final String id) {
        boolean result = dslContext.delete(JAVAOBJECT).where(JAVAOBJECT.SOURCE_ID.eq(id)).execute() == 1;
        log.info("Result of deleting object with id {} from table: {}", id, result);
        return result;
    }
}
