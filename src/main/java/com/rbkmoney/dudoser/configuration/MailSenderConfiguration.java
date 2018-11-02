package com.rbkmoney.dudoser.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.xbill.DNS.*;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

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

    @Value("${mail.smtp.auth}")
    boolean smtpsAuth;

    @Value("${mail.smtp.starttls.enable}")
    boolean starttls;

    @Value("${mail.smtp.timeout:30000}")
    int timeout;

    @Bean
    public Properties mailProperties() {
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.auth", smtpsAuth);
        javaMailProperties.put("mail.smtp.starttls.enable", starttls);
        javaMailProperties.put("mail.transport.protocol", protocol);
        javaMailProperties.put("mail.smtp.connectiontimeout", timeout);
        javaMailProperties.put("mail.smtp.timeout", timeout);
        return javaMailProperties;
    }

    @Bean
    public List<JavaMailSender> javaMailSender(Properties mailProperties) {
        try {
            Record[]  records = new Lookup(host, Type.MX).run();
            if (records == null) {
                throw new RuntimeException("Not found MX-records for host " + host);
            }
            return Arrays.stream(records).map(r -> {
                JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
                String host = ((MXRecord) r).getTarget().toString(true);
                System.out.println("MX-record: " + host);
                mailSender.setHost(host);
                mailSender.setPort(port);
                mailSender.setUsername(username);
                mailSender.setPassword(password);
                mailSender.setJavaMailProperties(mailProperties);
                return mailSender;
            }).collect(Collectors.toList());
        } catch (TextParseException e) {
            throw new RuntimeException(e);
        }
    }
}
