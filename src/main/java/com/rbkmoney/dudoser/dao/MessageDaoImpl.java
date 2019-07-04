package com.rbkmoney.dudoser.dao;

import com.rbkmoney.dudoser.dao.model.MessageToSend;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedRuntimeException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.JdbcUpdateAffectedIncorrectNumberOfRowsException;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.retry.annotation.Retryable;

import javax.sql.DataSource;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class MessageDaoImpl extends NamedParameterJdbcDaoSupport implements MessageDao {

    public MessageDaoImpl(DataSource dataSource) {
        setDataSource(dataSource);
    }

    @Override
    public boolean store(String receiver, String subject, String text) {
        final String sql = "INSERT INTO dudos.mailing_list(subject, receiver, body, date_created, sent) " +
                "VALUES (:subject, :receiver, :body, :date_created, :sent)" +
                "ON CONFLICT DO NOTHING;";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("subject", subject)
                .addValue("receiver", receiver)
                .addValue("body", text)
                .addValue("dateCreated", Instant.now())
                .addValue("sent", false);
        try {
            int updateCount = getNamedParameterJdbcTemplate().update(sql, params);
            if (updateCount != 1 && updateCount != 0) {
                return false;
            }
        } catch (NestedRuntimeException e) {
            throw new DaoException("MessageDaoImpl.store error with subject {}" + subject, e);
        }
        log.debug("Mail with subject: {}, to receiver: {} added to table", subject, receiver);
        return true;
    }

    @Override
    public List<MessageToSend> getUnsentMessages() {
        final String sql = "SELECT * FROM dudos.mailing_list WHERE sent = false;";
        try {
            return getNamedParameterJdbcTemplate()
                    .queryForList(sql, EmptySqlParameterSource.INSTANCE, MessageToSend.class);
        } catch (EmptyResultDataAccessException e) {
            //do nothing
        } catch (NestedRuntimeException e) {
            throw new DaoException("Can't fetch messages for sending", e);
        }
        return List.of();
    }

    @Override
    public boolean deleteSentMessages(LocalDateTime before) {
        final String sql = "DELETE FROM dudos.mailing_list WHERE sent = true AND date_created < :before_date;";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("before_date", before);
        try {
            int updateCount = getNamedParameterJdbcTemplate().update(sql, params);
            if (updateCount < 1) {
                return false;
            }
        } catch (NestedRuntimeException e) {
            log.error("Can't delete messages", e);
            return false;
        }
        return true;
    }

    @Override
    @Retryable
    public void markAsSent(List<MessageToSend> messages) {
        final String sql = "UPDATE dudos.mailing_list SET sent = true " +
                "WHERE subject = :subject;";

        List<MapSqlParameterSource> params = messages.stream()
                .map(message -> new MapSqlParameterSource("subject", message.getSubject()))
                .collect(Collectors.toList());

        try {
            int[] rowsPerBatchAffected = getNamedParameterJdbcTemplate().batchUpdate(sql, params.toArray(new SqlParameterSource[0]));
            if (rowsPerBatchAffected.length != params.size()) {
                throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(sql, params.size(), rowsPerBatchAffected.length);
            }

            for (int rowsAffected : rowsPerBatchAffected) {
                if (rowsAffected != 1) {
                    throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(sql, 1, rowsAffected);
                }
            }
        } catch (NestedRuntimeException e) {
            throw new DaoException("Can't mark as sent messages {}" + messages, e);
        }
    }
}
