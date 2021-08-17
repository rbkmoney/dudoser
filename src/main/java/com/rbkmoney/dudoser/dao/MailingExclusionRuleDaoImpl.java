package com.rbkmoney.dudoser.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import java.util.List;

@Slf4j
@Repository
public class MailingExclusionRuleDaoImpl extends NamedParameterJdbcDaoSupport implements MailingExclusionRuleDao {

    public MailingExclusionRuleDaoImpl(DataSource dataSource) {
        setDataSource(dataSource);
    }

    private final RowMapper<MailingExclusionRule> mapper = (rs, rowNum) -> {
        MailingExclusionRule result = new MailingExclusionRule();
        result.setId(rs.getLong("id"));
        result.setName(rs.getString("name"));
        result.setType(MailingExclusionRuleType.fromCode(rs.getString("type")));
        result.setValue(rs.getString("value"));
        return result;
    };

    @Override
    public Long createExclusionRule(MailingExclusionRule messageExclusionRule) {
        log.debug("Saving exclusion rule: {}", messageExclusionRule);
        final String sql = """
                    INSERT INTO dudos.mailing_exclusion_rules(name, type, value)
                    VALUES (:name, :type, :value)
                    RETURNING id;
                """;
        final MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", messageExclusionRule.getName())
                .addValue("type", messageExclusionRule.getType().getCode())
                .addValue("value", messageExclusionRule.getValue());
        Long id;
        try {
            id = getNamedParameterJdbcTemplate().queryForObject(sql, params, Long.class);
        } catch (Exception e) {
            throw new DaoException("Error occurred during saving exclusion rule " + messageExclusionRule, e);
        }
        log.debug("Exclusion rule has been saved with id = {}", id);
        return id;
    }

    @Override
    public MailingExclusionRule getExclusionRule(Long id) {
        log.debug("Getting exclusion rule by id = {}", id);
        final String sql = """
                    SELECT id, name, type, value
                    FROM dudos.mailing_exclusion_rules
                    WHERE id = :id;
                """;
        final MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        MailingExclusionRule exclusionRule;
        try {
            exclusionRule = getNamedParameterJdbcTemplate().query(sql, params, mapper).get(0);
        } catch (Exception e) {
            throw new DaoException("Error occurred during getting exclusion rule id = " + id, e);
        }
        log.debug("Got exclusion rule {}", exclusionRule);
        return exclusionRule;
    }

    @Override
    public List<MailingExclusionRule> getExclusionRules(MailingExclusionRuleType type) {
        log.debug("Getting exclusion rules by type = {}", type);
        final String sql = """
                    SELECT id, name, type, value
                    FROM dudos.mailing_exclusion_rules
                    WHERE type = :type;
                """;
        final MapSqlParameterSource params = new MapSqlParameterSource("type", type.getCode());
        List<MailingExclusionRule> exclusionRules;
        try {
            exclusionRules = getNamedParameterJdbcTemplate().query(sql, params, mapper);
        } catch (Exception e) {
            throw new DaoException("Error occurred during getting exclusion rules by type = " + type, e);
        }
        log.debug("Got exclusion rules {}", exclusionRules);
        return exclusionRules;
    }

    @Override
    public void removeExclusionRule(Long id) {
        log.debug("Deleting exclusion rules by id = {}", id);
        final String sql = "DELETE FROM dudos.mailing_exclusion_rules WHERE id = :id;";
        final MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        try {
            getNamedParameterJdbcTemplate().update(sql, params);
        } catch (Exception e) {
            throw new DaoException("Error occurred during deleting exclusion rules by id = " + id, e);
        }
        log.debug("Exclusion rules by id = {} has been deleted", id);
    }
}
