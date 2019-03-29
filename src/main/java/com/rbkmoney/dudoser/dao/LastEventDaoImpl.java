package com.rbkmoney.dudoser.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

import javax.sql.DataSource;

/**
 * Created by inal on 24.11.2016.
 */
public class LastEventDaoImpl extends NamedParameterJdbcDaoSupport implements LastEventDao {

    public LastEventDaoImpl(DataSource ds) {
        setDataSource(ds);
    }

    @Override
    public Long get(int id) {
        final String sql = "SELECT event_id FROM dudos.last_event_id WHERE id=:id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        try {
            return getNamedParameterJdbcTemplate().queryForObject(sql, params, Long.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void set(Long eventId, int id) {
        final String sql = "INSERT INTO dudos.last_event_id(id, event_id) " +
                "VALUES(:id, :event_id) " +
                "ON CONFLICT (id) DO UPDATE " +
                "SET event_id =:event_id";
        MapSqlParameterSource params = new MapSqlParameterSource("event_id", eventId)
                .addValue("id", id);
        int x = getNamedParameterJdbcTemplate().update(sql, params);
        if (x != 1) {
            throw new DaoException("Couldn't set last event with id " + eventId);
        }
    }
}
