package com.rbkmoney.dudoser.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

import javax.sql.DataSource;

/**
 * Created by inal on 28.11.2016.
 */
public class TemplateDaoImpl extends NamedParameterJdbcDaoSupport implements TemplateDao {

    public TemplateDaoImpl(DataSource ds) {
        this.setDataSource(ds);
    }

    //TODO Refactoring!!!
    @Override
    public String getTemplateBodyByMerchShopParams(String typeCode, long merchId, long shopId) {

        final String sql =
                "select t.body \n" +
                "from dudos.templates t, \n" +
                "dudos.merchant_shop_template_types mstt, \n" +
                "dudos.merchant_shop_bind msb \n" +
                "where mstt.id=msb.type and msb.template_id=t.id and mstt.code=:code and msb.merch_id=:merch_id and msb.shop_id=:shop_id \n" +
                "union all\n" +
                "select t.id, t.body \n" +
                "from dudos.templates t, \n" +
                "dudos.merchant_shop_template_types mstt, \n" +
                "dudos.merchant_shop_bind msb \n" +
                "where mstt.id=msb.type and msb.template_id=t.id and mstt.code=:code and msb.merch_id=:merch_id and msb.shop_id is null\n" +
                "union all\n" +
                "select t.id, t.body \n" +
                "from dudos.templates t, \n" +
                "dudos.merchant_shop_template_types mstt, \n" +
                "dudos.merchant_shop_bind msb \n" +
                "where mstt.id=msb.type and msb.template_id=t.id and mstt.code=:code and msb.merch_id is null and msb.shop_id is null";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("code", typeCode);
        params.addValue("merch_id", merchId);
        params.addValue("shopId", shopId);

        try {
            return getNamedParameterJdbcTemplate().queryForList(sql, params, String.class).get(0);
        } catch (Exception e) {
            logger.error("Couldn't find template", e);
            throw new RuntimeException("Couldn't find template with params "+params);
        }
    }

    @Override
    public String getTemplateBodyByTypeCode(String typeCode) {
        final String sql =
                        "select t.body \n" +
                        "from dudos.templates t, \n" +
                        "dudos.merchant_shop_template_types mstt, \n" +
                        "dudos.merchant_shop_bind msb \n" +
                        "where mstt.id=msb.type and msb.template_id=t.id and mstt.code=:code and msb.merch_id is null and msb.shop_id is null";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("code", typeCode);

        try {
            return getNamedParameterJdbcTemplate().queryForList(sql, params, String.class).get(0);
        } catch (Exception e) {
            logger.error("Couldn't find template", e);
            throw new RuntimeException("Couldn't find template with params "+params);
        }
    }
}
