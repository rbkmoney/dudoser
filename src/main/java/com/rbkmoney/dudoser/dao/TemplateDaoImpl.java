package com.rbkmoney.dudoser.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NestedRuntimeException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by inal on 28.11.2016.
 */
public class TemplateDaoImpl extends NamedParameterJdbcDaoSupport implements TemplateDao {
    Logger log = LoggerFactory.getLogger(this.getClass());

    public TemplateDaoImpl(DataSource ds) {
        this.setDataSource(ds);
    }

    @Override
    public Template getTemplateBodyByMerchShopParams(EventTypeCode typeCode, String merchId, String shopId) {
        log.debug("getTemplateBodyByMerchShopParams request. TypeCode = {}, merchId = {}, shopId = {}", typeCode, merchId, shopId);
        final String sql =
                "select body, is_active from (" +
                        "select 1 as id, t.body, msb.is_active " +
                        "from dudos.templates t, " +
                        "dudos.merchant_shop_template_types mstt, " +
                        "dudos.merchant_shop_bind msb " +
                        "where mstt.id=msb.type and msb.template_id=t.id and mstt.code=:code and msb.merch_id=:merch_id and msb.shop_id=:shop_id " +
                        "union all " +
                        "select 2 as id, t.body, msb.is_active " +
                        "from dudos.templates t, " +
                        "dudos.merchant_shop_template_types mstt, " +
                        "dudos.merchant_shop_bind msb " +
                        "where mstt.id=msb.type and msb.template_id=t.id and mstt.code=:code and msb.merch_id=:merch_id and msb.shop_id is null " +
                        "union all " +
                        "select 3 as id, t.body, msb.is_active " +
                        "from dudos.templates t, " +
                        "dudos.merchant_shop_template_types mstt, " +
                        "dudos.merchant_shop_bind msb " +
                        "where mstt.id=msb.type and msb.template_id=t.id and mstt.code=:code and msb.merch_id is null and msb.shop_id is null " +
                        ") as full_select order by id asc limit 1";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("code", typeCode.getCode());
        params.addValue("merch_id", merchId);
        params.addValue("shop_id", shopId);

        try {
            Template result = getNamedParameterJdbcTemplate().queryForObject(sql, params, new RowMapper<Template>() {
                @Override
                public Template mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return new Template(rs.getString("body"), rs.getBoolean("is_active"));
                }
            });
            return result;
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (NestedRuntimeException e) {
            log.warn("Couldn't find template", e);
            throw new DaoException("Couldn't find template with typeCode = " + typeCode + "; merchId = " + merchId + "; shopId = " + shopId);
        }
    }

    @Override
    public Template getTemplateBodyByTypeCode(EventTypeCode typeCode) {
        log.debug("New getTemplateBodyByTypeCode request. TypeCode = {}", typeCode);
        final String sql =
                "select t.body, msb.is_active " +
                        "from dudos.templates t, " +
                        "dudos.merchant_shop_template_types mstt, " +
                        "dudos.merchant_shop_bind msb " +
                        "where mstt.id=msb.type and msb.template_id=t.id and mstt.code=:code and msb.merch_id is null and msb.shop_id is null";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("code", typeCode.getCode());
        try {
            Template result = getNamedParameterJdbcTemplate().queryForObject(sql, params, new BeanPropertyRowMapper<>(Template.class));
            log.debug("Response getTemplateBodyByTypeCode. TypeCode = {}", typeCode);
            return result;
        } catch (NestedRuntimeException e) {
            log.warn("Couldn't find template", e);
            throw new DaoException("Couldn't find template with typeCode = " + typeCode);
        }
    }
}
