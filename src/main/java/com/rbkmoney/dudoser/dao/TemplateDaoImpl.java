package com.rbkmoney.dudoser.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedRuntimeException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

import javax.sql.DataSource;

/**
 * Created by inal on 28.11.2016.
 */
@Slf4j
public class TemplateDaoImpl extends NamedParameterJdbcDaoSupport implements TemplateDao {

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
            return getNamedParameterJdbcTemplate().queryForObject(sql, params, (rs, rowNum) -> new Template(rs.getString("body"), rs.getBoolean("is_active")));
        } catch (NestedRuntimeException e) {
            log.warn("Couldn't find template", e);
            throw new DaoException("Couldn't find template with typeCode = " + typeCode + "; merchId = " + merchId + "; shopId = " + shopId);
        }
    }
}
