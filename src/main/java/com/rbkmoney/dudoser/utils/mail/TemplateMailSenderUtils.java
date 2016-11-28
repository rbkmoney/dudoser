package com.rbkmoney.dudoser.utils.mail;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.NoSuchFileException;
import java.util.Map;

/**
 * Created by inal on 18.11.2016.
 */
@Component
public class TemplateMailSenderUtils extends MailSenderUtils {

    private static Logger log = LoggerFactory.getLogger(TemplateMailSenderUtils.class);

    @Autowired
    Configuration freemarkerConfiguration;

    private String freeMarkerTemplateContent;
    private Map<String, Object> model;

    public boolean send(String from, String to, String subject) {
        try {
            return super.send(from, to, subject, getFilledFreeMarkerTemplateContent(), null);
        } catch (Exception e) {
            log.error("Exception TemplateMailSenderUtils", e);
        }
        return false;
    }

    public String getFilledFreeMarkerTemplateContent() {

        Template t = null;
        try {
            t = new Template("templateName", new StringReader(getFreeMarkerTemplateContent()), freemarkerConfiguration);
            Writer out = new StringWriter();
            t.process(getModel(), out);
            return out.toString();
        } catch (IOException e) {
            log.error("Throwing IOException while template processing", e);
            throw new RuntimeException(e);
        } catch (TemplateException e) {
            log.error("Throwing TemlplateException while template processing", e);
            throw new RuntimeException(e);
        }
    }

    public void setFreeMarkerTemplateContent(String freeMarkerTemplateContent) {
        this.freeMarkerTemplateContent = freeMarkerTemplateContent;
    }

    public String getFreeMarkerTemplateContent() {
        return freeMarkerTemplateContent;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public TemplateMailSenderUtils setModel(Map<String, Object> model) {
        this.model = model;
        return this;
    }

}
