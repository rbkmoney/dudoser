package com.rbkmoney.dudoser.configuration;

import com.rbkmoney.dudoser.dao.LastEventDao;
import com.rbkmoney.dudoser.dao.LastEventDaoImpl;
import com.rbkmoney.dudoser.domain.Dudos;
import org.jooq.Schema;
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
    public Schema dbSchema() {
        return Dudos.DUDOS;
    }

}
