package com.rbkmoney.dudoser.dao;

import com.rbkmoney.dudoser.AbstractIntegrationTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TemplateDaoImplTest extends AbstractIntegrationTest {
    @Autowired
    TemplateDao templateDao;
    @Test
    public void getTemplateBodyByMerchShopParams() {
        Template template = templateDao.getTemplateBodyByMerchShopParams(EventTypeCode.PAYMENT_STATUS_CHANGED_PROCESSED, "1", "2");
        Assert.assertNotNull(template);
        Assert.assertTrue(template.isActive());
        template = templateDao.getTemplateBodyByMerchShopParams(EventTypeCode.REFUND_STATUS_CHANGED_SUCCEEDED, "1", "2");
        Assert.assertNotNull(template);
        Assert.assertTrue(template.isActive());
        template = templateDao.getTemplateBodyByMerchShopParams(EventTypeCode.PAYMENT_STATUS_CHANGED_PROCESSED, "d6f1c81f-7600-4aae-aa59-5a79fe634a3d", "3ad07fb1-56e4-47c3-8c7d-a9521b7ce70e");
        Assert.assertNotNull(template);
        Assert.assertFalse(template.isActive());
        template = templateDao.getTemplateBodyByMerchShopParams(EventTypeCode.REFUND_STATUS_CHANGED_SUCCEEDED, "d6f1c81f-7600-4aae-aa59-5a79fe634a3d", "3ad07fb1-56e4-47c3-8c7d-a9521b7ce70e");
        Assert.assertNotNull(template);
        Assert.assertFalse(template.isActive());
        template = templateDao.getTemplateBodyByMerchShopParams(EventTypeCode.PAYMENT_STATUS_CHANGED_PROCESSED, "a1bc0bf1-5659-4e84-aad1-36d6ae7b9deb", "7900b13f-b79a-47a3-8eb9-357a24429efe");
        Assert.assertNotNull(template);
        Assert.assertTrue(template.isActive());
        template = templateDao.getTemplateBodyByMerchShopParams(EventTypeCode.PAYMENT_STATUS_CHANGED_PROCESSED, "a1bc0bf1-5659-4e84-aad1-36d6ae7b9deb", "7f48d7bd-d48f-44a3-badb-6bba0939b554 ");
        Assert.assertNotNull(template);
        Assert.assertTrue(template.isActive());
    }
}
