package com.rbkmoney.dudoser.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.xbill.DNS.*;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

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
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(10);
        return  taskScheduler;
    }

    @Bean
    public List<JavaMailSender> javaMailSender(@Value("${mail.host}") String host,
                                               @Value("${mail.port}") int port,
                                               @Value("${mail.username}") String username,
                                               @Value("${mail.password}") String password,
                                               Properties mailProperties) {
        try {
            Record[]  records = new Lookup(host, Type.MX).run();
            if (records == null) {
                throw new RuntimeException("Not found MX-records for host " + host);
            }
            return Arrays.stream(records).map(r -> {
                JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
                String mxHost = ((MXRecord) r).getTarget().toString(true);
                mailSender.setHost(mxHost);
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
