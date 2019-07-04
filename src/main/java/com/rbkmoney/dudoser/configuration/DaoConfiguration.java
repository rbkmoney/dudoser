package com.rbkmoney.dudoser.configuration;

import com.rbkmoney.dudoser.dao.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DaoConfiguration {

    @Bean
    public LastEventDao lastEventDao(DataSource dataSource) {
        return new LastEventDaoImpl(dataSource);
    }

    @Bean
    public TemplateDao templateDao(DataSource dataSource) {
        return new TemplateDaoImpl(dataSource);
    }

    @Bean
    public PaymentPayerDao paymentPayerDao(DataSource dataSource) {
        return new PaymentPayerDaoImpl(dataSource);
    }

    @Bean
    public MessageDao messageDao(DataSource dataSource) {
        return new MessageDaoImpl(dataSource);
    }
}
