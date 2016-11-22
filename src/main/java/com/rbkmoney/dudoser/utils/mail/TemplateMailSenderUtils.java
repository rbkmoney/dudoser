package com.rbkmoney.dudoser.utils.mail;

import freemarker.template.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

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

    private String fileNameTemplate;
    private Map<String, Object> model;

        public boolean send(String from, String to, String subject) {
        try {
            return super.send(from, to, subject, getFreeMarkerTemplateContent(), null);
        } catch (NoSuchFileException e) {
            log.error("Exception TemplateMailSenderUtils", e);
        }
        return false;
    }

    private String getFreeMarkerTemplateContent() throws NoSuchFileException {
        try {
            return FreeMarkerTemplateUtils.processTemplateIntoString(
                    freemarkerConfiguration.getTemplate(getFileNameTemplate()),
                    getModel()
            );
        } catch (Exception e) {
            log.error("Exception occured while processing " + getFileNameTemplate(), e);
            throw new NoSuchFileException(getFileNameTemplate() + e.getMessage());
        }
    }

    public String getFileNameTemplate() {
        return fileNameTemplate;
    }

    public TemplateMailSenderUtils setFileNameTemplate(String fileNameTemplate) {
        this.fileNameTemplate = fileNameTemplate;
        return this;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public TemplateMailSenderUtils setModel(Map<String, Object> model) {
        this.model = model;
        return this;
    }

}
