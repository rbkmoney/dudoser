package com.rbkmoney.dudoser.service.template;

import com.rbkmoney.dudoser.TestData;
import com.rbkmoney.dudoser.dao.model.Content;
import com.rbkmoney.dudoser.service.template.model.KebMetadata;
import freemarker.template.TemplateModelException;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class JsonParseMethodTest {

    @Test
    public void execTest() throws TemplateModelException {
        List<String> params = new ArrayList<>();
        Content content = new Content();
        content.setData(TestData.kebMetadata());
        params.add(content.getDataValue());
        params.add(KebMetadata.class.getName());
        KebMetadata exec = (KebMetadata) new JsonParseMethod().exec(params);
        Assert.assertNotNull(exec);
    }

    @Test(expected = TemplateModelException.class)
    public void execParamExceptionTest() throws TemplateModelException {
        List<String> params = new ArrayList<>();
        params.add("");
        KebMetadata exec = (KebMetadata) new JsonParseMethod().exec(params);
    }

}
