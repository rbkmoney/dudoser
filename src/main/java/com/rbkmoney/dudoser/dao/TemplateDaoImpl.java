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
        params.addValue("code", typeCode.toString());
        params.addValue("merch_id", merchId);
        params.addValue("shop_id", shopId);
        log.debug("Params for select template: typeCode = {}, merchId = {}, shopId = {}", typeCode, merchId, shopId);

        try {
            return getNamedParameterJdbcTemplate().queryForList(sql, params, String.class).get(0);
        } catch (NestedRuntimeException | IndexOutOfBoundsException e) {
            logger.error("Couldn't find template", e);
            throw new DaoException("Couldn't find template with typeCode=" + typeCode + ";merchId=" + merchId + ";shopId=" + shopId);
        }
    }

    @Override
    public String getTemplateBodyByTypeCode(EventTypeCode typeCode) {
        final String sql =
                "select t.body \n" +
                        "from dudos.templates t, \n" +
                        "dudos.merchant_shop_template_types mstt, \n" +
                        "dudos.merchant_shop_bind msb \n" +
                        "where mstt.id=msb.type and msb.template_id=t.id and mstt.code=:code and msb.merch_id is null and msb.shop_id is null";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("code", typeCode.toString());
        try {
            return getNamedParameterJdbcTemplate().queryForObject(sql, params, String.class);
        } catch (NestedRuntimeException e) {
            logger.error("Couldn't find template", e);
            throw new DaoException("Couldn't find template with typeCode=" + typeCode);
        }
    }
}
