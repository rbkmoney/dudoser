package com.rbkmoney.dudoser.configuration;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailSenderConfiguration {
    @Bean
    public Properties mailProperties(@Value("${mail.protocol}") String protocol,
                                     @Value("${mail.smtp.auth}") boolean smtpsAuth,
                                     @Value("${mail.smtp.starttls.enable}") boolean starttls,
                                     @Value("${mail.smtp.timeout:30000}") int timeout) {
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.auth", smtpsAuth);
        javaMailProperties.put("mail.smtp.starttls.enable", starttls);
        javaMailProperties.put("mail.transport.protocol", protocol);
        javaMailProperties.put("mail.smtp.connectiontimeout", timeout);
        javaMailProperties.put("mail.smtp.timeout", timeout);
        return javaMailProperties;
    }

    @Bean
    public ExecutorService mailSendingExecutorService(@Value("${message.sending.concurrency:8}") Integer concurrency) {
        return Executors.newFixedThreadPool(concurrency);
    }

    @Bean
    public JavaMailSender javaMailSender(@Value("${mail.host}") String host,
                                         @Value("${mail.port}") int port,
                                         @Value("${mail.username}") String username,
                                         @Value("${mail.password}") String password,
                                         Properties mailProperties) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        mailSender.setJavaMailProperties(mailProperties);
        return mailSender;
    }
}
