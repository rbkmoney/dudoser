package com.rbkmoney.dudoser.dao;

/**
 * Created by inal on 28.11.2016.
 */
public interface TemplateDao {
    String getTemplateBodyByMerchShopParams( String typeCode, long merchId, long shopId);
    String getTemplateBodyByTypeCode(String typeCode);
}
