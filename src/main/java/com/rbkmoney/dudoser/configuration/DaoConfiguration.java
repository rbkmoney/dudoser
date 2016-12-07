package com.rbkmoney.dudoser.configuration;

import com.rbkmoney.dudoser.dao.*;
import org.jooq.Schema;
import org.jooq.impl.SchemaImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.sql.DataSource;

@Configuration
public class DaoConfiguration {

    @Bean
    @DependsOn("dbInitializer")
    public LastEventDao lastEventDao(DataSource dataSource) {
        return new LastEventDaoImpl(dataSource);
    }

    @Bean
    @DependsOn("dbInitializer")
    public TemplateDao templateDao(DataSource dataSource) {
        return new TemplateDaoImpl(dataSource);
    }

    @Bean
    @DependsOn("dbInitializer")
    public PaymentPayerDao paymentPayerDao(DataSource dataSource) {
        return new InMemoryPaymentPayerDao(dataSource);
    }

    @Bean
    public Schema dbSchema() {
        return new SchemaImpl("dudos");
    }
}
