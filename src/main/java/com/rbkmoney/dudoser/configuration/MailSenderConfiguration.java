package com.rbkmoney.dudoser.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.Properties;

@Configuration
public class MailSenderConfiguration {

    @Value("${mail.host}")
    String host;

    @Value("${mail.port}")
    int port;

    @Value("${mail.username}")
    String username;

    @Value("${mail.password}")
    String password;

    @Value("${mail.protocol}")
    String protocol;

    @Value("${mail.smtps.auth}")
    boolean smtpsAuth;

    @Bean
    public Properties mailProperties() {
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtps.auth", smtpsAuth);
        javaMailProperties.put("mail.transport.protocol", protocol);
        return javaMailProperties;
    }

    @Bean
    public JavaMailSender javaMailSender(Properties mailProperties) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        mailSender.setJavaMailProperties(mailProperties);
        return mailSender;
    }

    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setTemplateLoaderPath("classpath:templates/");
        configurer.setPreferFileSystemAccess(false);
        configurer.setDefaultEncoding("UTF-8");
        return configurer;
    }

}
