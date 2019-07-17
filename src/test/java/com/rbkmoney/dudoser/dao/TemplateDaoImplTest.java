package com.rbkmoney.dudoser.dao;

import com.rbkmoney.dudoser.AbstractIntegrationTest;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Ignore
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
    }
}
