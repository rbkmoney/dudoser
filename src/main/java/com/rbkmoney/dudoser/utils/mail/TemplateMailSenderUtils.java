package com.rbkmoney.dudoser.utils.mail;

import com.rbkmoney.dudoser.exception.MailNotSendException;
import com.rbkmoney.dudoser.exception.UnknownException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/**
 * Created by inal on 18.11.2016.
 */
@Component
public class TemplateMailSenderUtils extends MailSenderUtils {

    private static Logger log = LoggerFactory.getLogger(TemplateMailSenderUtils.class);
    private static final String UNUSED_TEMPLATE_NAME = "templateName";

    @Autowired
    Configuration freemarkerConfiguration;

    private String freeMarkerTemplateContent;
    private Map<String, Object> model;

    public void send(String from, String[] to, String subject) throws MailNotSendException {
        super.send(from, to, subject, getFilledFreeMarkerTemplateContent(), null);
    }

    public String getFilledFreeMarkerTemplateContent() {
        Template t = null;
        try {
            t = new Template(UNUSED_TEMPLATE_NAME, new StringReader(getFreeMarkerTemplateContent()), freemarkerConfiguration);
            Writer out = new StringWriter();
            t.process(getModel(), out);
            return out.toString();
        } catch (IOException | TemplateException e) {
            log.error("Throwing unknown exception while template processing", e);
            throw new UnknownException(e);
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
