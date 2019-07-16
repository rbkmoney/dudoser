package com.rbkmoney.dudoser.dao;

import com.rbkmoney.dudoser.dao.model.MessageToSend;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedRuntimeException;
import org.springframework.jdbc.JdbcUpdateAffectedIncorrectNumberOfRowsException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.retry.annotation.Retryable;

import javax.sql.DataSource;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
                .addValue("date_created", LocalDateTime.now(ZoneOffset.UTC))
                .addValue("sent", false);
        try {
            int updateCount = getNamedParameterJdbcTemplate().update(sql, params);
            if (updateCount != 1 && updateCount != 0) {
                return false;
            }
        } catch (NestedRuntimeException e) {
            throw new DaoException("MessageDaoImpl.store error with subject " + subject, e);
        }
        log.debug("Mail with subject: {}, to receiver: {} added to table", subject, receiver);
        return true;
    }

    @Override
    public List<MessageToSend> getUnsentMessages() {
        final String sql = "SELECT subject, receiver, body, date_created, sent " +
                "FROM dudos.mailing_list " +
                "WHERE sent = false " +
                "LIMIT 100;";
        try {
            return getNamedParameterJdbcTemplate()
                    .query(sql, new BeanPropertyRowMapper<>(MessageToSend.class, true));
        } catch (NestedRuntimeException e) {
            throw new DaoException("Can't fetch messages for sending", e);
        } catch (Exception e) {
            log.error("Error while fetching messages", e);
            return List.of();
        }
    }

    @Override
    public void deleteMessages(Instant before, Boolean sent) {
        final String sql = "DELETE FROM dudos.mailing_list WHERE sent = :sent AND date_created < :before_date;";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("sent", sent)
                .addValue("before_date", LocalDateTime.ofInstant(before, ZoneOffset.UTC));
        try {
            int updateCount = getNamedParameterJdbcTemplate().update(sql, params);
            log.info("Deleted {} messages with status sent={}", updateCount, sent);
        } catch (NestedRuntimeException e) {
            log.error("Can't delete messages", e);
        }
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
        } catch (NestedRuntimeException e) {
            throw new DaoException("Can't mark as sent messages " + messages, e);
        }
    }
}
