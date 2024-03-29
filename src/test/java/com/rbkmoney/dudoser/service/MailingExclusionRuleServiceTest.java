package com.rbkmoney.dudoser.service;

import com.rbkmoney.dudoser.dao.MailingExclusionRule;
import com.rbkmoney.dudoser.dao.MailingExclusionRuleDao;
import com.rbkmoney.dudoser.dao.MailingExclusionRuleType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MailingExclusionRuleServiceTest {

    @MockBean
    private MailingExclusionRuleDao dao;

    @Autowired
    private MailingExclusionRuleService service;

    @Test
    void shouldCreateExclusionRule() {
        MailingExclusionRule executionRule = new MailingExclusionRule();
        executionRule.setType(MailingExclusionRuleType.SHOP);
        executionRule.setId(1L);

        Mockito.when(dao.getByType(executionRule.getType())).thenReturn(new ArrayList<>());
        Mockito.when(dao.create(executionRule)).thenReturn(1L);

        MailingExclusionRule actual = service.createExclusionRule(executionRule);

        Assert.assertEquals(executionRule, actual);

        Mockito.verify(dao).getByType(executionRule.getType());
        Mockito.verify(dao).create(executionRule);
    }

    @Test
    void shouldThrowExceptionDuringExclusionRuleCreation() {
        MailingExclusionRule executionRule = new MailingExclusionRule();
        executionRule.setType(MailingExclusionRuleType.SHOP);

        Mockito.when(dao.getByType(executionRule.getType()))
                .thenReturn(List.of(new MailingExclusionRule()));

        assertThrows(IllegalStateException.class, () -> {
            service.createExclusionRule(executionRule);
        });

        Mockito.verify(dao).getByType(executionRule.getType());
        Mockito.verifyNoMoreInteractions(dao);
    }
}
