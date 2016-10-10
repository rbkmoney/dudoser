package com.rbkmoney.dudoser.utils.mail;

import freemarker.template.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.nio.file.NoSuchFileException;
import java.util.Map;

@Component
public class MailSenderUtils {

    private static Logger log = LoggerFactory.getLogger(MailSenderUtils.class);

    private String fileNameTemplate;
    private Map<String, Object> model;

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    Configuration freemarkerConfiguration;

    public boolean send(String from, String to, String subject) {
        boolean isSuccess = false;

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);

            String text = getFreeMarkerTemplateContent();
            log.info("Template content: {}", text);

            helper.setText(text, true);

            File logo = new File(ClassLoader.getSystemResource("images/logo.png").getFile());
            FileSystemResource res = new FileSystemResource(logo);
            helper.addInline("identifierLogo", res);

            mailSender.send(message);
            isSuccess = true;
        } catch (Exception e) {
            log.error("Exception MailUtils", e);
        }

        return isSuccess;
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

    public MailSenderUtils setFileNameTemplate(String fileNameTemplate) {
        this.fileNameTemplate = fileNameTemplate;
        return this;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public MailSenderUtils setModel(Map<String, Object> model) {
        this.model = model;
        return this;
    }

}
