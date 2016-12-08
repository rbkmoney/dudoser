package com.rbkmoney.dudoser.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

import javax.sql.DataSource;
import java.util.HashMap;

/**
 * Created by inal on 24.11.2016.
 */
public class LastEventDaoImpl extends NamedParameterJdbcDaoSupport implements LastEventDao {

    public LastEventDaoImpl(DataSource ds) {
        setDataSource(ds);
    }

    @Override
    public Long get() {
        final String sql = "SELECT event_id FROM dudos.last_event_id WHERE id=1";
        try {
            return getNamedParameterJdbcTemplate().queryForObject(sql, new HashMap<>(), Long.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void set(Long id) {
        final String sql = "UPDATE dudos.last_event_id SET event_id =:id where id=1";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        int x = getNamedParameterJdbcTemplate().update(sql, params);
        if (x != 1) {
            throw new RuntimeException("Could not set las event");
        }
    }
}