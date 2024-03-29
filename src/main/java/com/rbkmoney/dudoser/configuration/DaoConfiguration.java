package com.rbkmoney.dudoser.configuration;

import com.rbkmoney.dudoser.dao.MailingExclusionRuleDao;
import com.rbkmoney.dudoser.dao.MailingExclusionRuleDaoImpl;
import com.rbkmoney.dudoser.dao.MessageDao;
import com.rbkmoney.dudoser.dao.MessageDaoImpl;
import com.rbkmoney.dudoser.dao.TemplateDao;
import com.rbkmoney.dudoser.dao.TemplateDaoImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

@Configuration
public class DaoConfiguration {

    @Bean
    public TemplateDao templateDao(DataSource dataSource) {
        return new TemplateDaoImpl(dataSource);
    }

    @Bean
    public MessageDao messageDao(DataSource dataSource) {
        return new MessageDaoImpl(dataSource);
    }

    @Bean
    public MailingExclusionRuleDao mailingExclusionRuleDao(DataSource dataSource) {
        return new MailingExclusionRuleDaoImpl(dataSource);
    }

    @Bean
    @Primary
    public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager,
                                                   @Value("${db.jdbc.tr_timeout}") int transactionTimeout) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        transactionTemplate.setTimeout(transactionTimeout);
        return transactionTemplate;
    }
}
