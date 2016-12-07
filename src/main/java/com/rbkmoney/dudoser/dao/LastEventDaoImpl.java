package com.rbkmoney.dudoser.dao;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import javax.sql.DataSource;

import static com.rbkmoney.dudoser.domain.Tables.LAST_EVENT_ID;

/**
 * Created by inal on 24.11.2016.
 */
public class LastEventDaoImpl implements LastEventDao {

    private DSLContext dslContext;

    public LastEventDaoImpl(DataSource ds) {
        dslContext = DSL.using(ds, SQLDialect.POSTGRES);
    }

    @Override
    public Long get() {
        return dslContext.select(LAST_EVENT_ID.EVENT_ID).from(LAST_EVENT_ID).where(LAST_EVENT_ID.ID.eq(1)).fetchAny().get(LAST_EVENT_ID.EVENT_ID);
    }

    @Override
    public void set(Long id) {
        dslContext.update(LAST_EVENT_ID).set(LAST_EVENT_ID.EVENT_ID, id).where(LAST_EVENT_ID.ID.eq(1)).execute();
    }
}
