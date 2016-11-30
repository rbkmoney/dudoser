package com.rbkmoney.dudoser.dao;

/**
 * Created by inal on 28.11.2016.
 */
public interface TemplateDao {
    String getTemplateBodyByMerchShopParams(EventTypeCode typeCode, String merchId, String shopId);

    String getTemplateBodyByTypeCode(EventTypeCode typeCode);
}
