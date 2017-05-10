package com.rbkmoney.dudoser.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NestedRuntimeException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

import javax.sql.DataSource;

/**
 * Created by inal on 28.11.2016.
 */
public class TemplateDaoImpl extends NamedParameterJdbcDaoSupport implements TemplateDao {
    Logger log = LoggerFactory.getLogger(this.getClass());

    public TemplateDaoImpl(DataSource ds) {
        this.setDataSource(ds);
    }

    //TODO Refactoring!!!
    @Override
    public String getTemplateBodyByMerchShopParams(EventTypeCode typeCode, String merchId, String shopId) {
        log.debug("getTemplateBodyByMerchShopParams request. TypeCode = {}, merchId = {}, shopId = {}", typeCode, merchId, shopId);
        final String sql =
                "select t.body \n" +
                        "from dudos.templates t, \n" +
                        "dudos.merchant_shop_template_types mstt, \n" +
                        "dudos.merchant_shop_bind msb \n" +
                        "where mstt.id=msb.type and msb.template_id=t.id and mstt.code=:code and msb.merch_id=:merch_id and msb.shop_id=:shop_id \n" +
                        "union all\n" +
                        "select t.body \n" +
                        "from dudos.templates t, \n" +
                        "dudos.merchant_shop_template_types mstt, \n" +
                        "dudos.merchant_shop_bind msb \n" +
                        "where mstt.id=msb.type and msb.template_id=t.id and mstt.code=:code and msb.merch_id=:merch_id and msb.shop_id is null\n" +
                        "union all\n" +
                        "select t.body \n" +
                        "from dudos.templates t, \n" +
                        "dudos.merchant_shop_template_types mstt, \n" +
                        "dudos.merchant_shop_bind msb \n" +
                        "where mstt.id=msb.type and msb.template_id=t.id and mstt.code=:code and msb.merch_id is null and msb.shop_id is null";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("code", typeCode.getCode());
        params.addValue("merch_id", merchId);
        params.addValue("shop_id", shopId);

        try {
            String result = getNamedParameterJdbcTemplate().queryForList(sql, params, String.class).get(0);
            log.debug("getTemplateBodyByMerchShopParams response. TypeCode = {}, merchId = {}, shopId = {}", typeCode, merchId, shopId);
            return result;
        } catch (NestedRuntimeException | IndexOutOfBoundsException e) {
            log.warn("Couldn't find template", e);
            throw new DaoException("Couldn't find template with typeCode = " + typeCode + "; merchId = " + merchId + "; shopId = " + shopId);
        }
    }

    @Override
    public String getTemplateBodyByTypeCode(EventTypeCode typeCode) {
        log.debug("New getTemplateBodyByTypeCode request. TypeCode = {}", typeCode);
        final String sql =
                "select t.body \n" +
                        "from dudos.templates t, \n" +
                        "dudos.merchant_shop_template_types mstt, \n" +
                        "dudos.merchant_shop_bind msb \n" +
                        "where mstt.id=msb.type and msb.template_id=t.id and mstt.code=:code and msb.merch_id is null and msb.shop_id is null";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("code", typeCode.getCode());
        try {
            String result = getNamedParameterJdbcTemplate().queryForObject(sql, params, String.class);
            log.debug("Response getTemplateBodyByTypeCode. TypeCode = {}", typeCode);
            return result;
        } catch (NestedRuntimeException e) {
            log.error("Couldn't find template", e);
            throw new DaoException("Couldn't find template with typeCode = " + typeCode);
        }
    }
}
