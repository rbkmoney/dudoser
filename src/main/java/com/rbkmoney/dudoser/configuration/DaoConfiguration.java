package com.rbkmoney.dudoser.configuration;

import com.rbkmoney.dudoser.dao.LastEventDao;
import com.rbkmoney.dudoser.dao.LastEventDaoImpl;
import com.rbkmoney.dudoser.dao.TemplateDao;
import com.rbkmoney.dudoser.dao.TemplateDaoImpl;
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
    public Schema dbSchema() {
        return new SchemaImpl("dudos");
    }
}
