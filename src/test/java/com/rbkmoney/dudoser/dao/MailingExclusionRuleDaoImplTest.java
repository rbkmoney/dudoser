package com.rbkmoney.dudoser.dao;

import com.rbkmoney.dudoser.AbstractIntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MailingExclusionRuleDaoImplTest extends AbstractIntegrationTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    MailingExclusionRuleDaoImpl dao;

    private MailingExclusionRule testDtoExclusionRule;

    @Before
    void setup() {
        jdbcTemplate.update("truncate table dudos.mailing_exclusion_rules;");

        testDtoExclusionRule = new MailingExclusionRule();
        testDtoExclusionRule.setName("test");
        testDtoExclusionRule.setType(MailingExclusionRuleType.SHOP);
        testDtoExclusionRule.setValue("1,2,3");
    }

    @Test
    void createExclusionRule() {
        Long id = dao.create(testDtoExclusionRule);

        MailingExclusionRule actual = dao.get(id);
        assertEquals(id, actual.getId());
        assertEquals(testDtoExclusionRule.getName(), actual.getName());
        assertEquals(testDtoExclusionRule.getType(), actual.getType());
        assertEquals(testDtoExclusionRule.getValue(), actual.getValue());
    }

    @Test
    void getExclusionRule() {
        Long id = dao.create(testDtoExclusionRule);

        MailingExclusionRule actual = dao.get(id);
        assertEquals(id, actual.getId());
        assertEquals(testDtoExclusionRule.getName(), actual.getName());
        assertEquals(testDtoExclusionRule.getType(), actual.getType());
        assertEquals(testDtoExclusionRule.getValue(), actual.getValue());
    }

    @Test
    void getExclusionRulesByType() {
        Long id = dao.create(testDtoExclusionRule);

        List<MailingExclusionRule> actual = dao.getByType(testDtoExclusionRule.getType());
        assertEquals(1, actual.size());
        assertEquals(id, actual.get(0).getId());
        assertEquals(testDtoExclusionRule.getName(), actual.get(0).getName());
        assertEquals(testDtoExclusionRule.getType(), actual.get(0).getType());
        assertEquals(testDtoExclusionRule.getValue(), actual.get(0).getValue());
    }

    @Test
    void getExclusionRulesByShopId() {
        Long id = dao.create(testDtoExclusionRule);

        List<MailingExclusionRule> actual = dao.getByShopId("1");
        assertEquals(1, actual.size());
        assertEquals(id, actual.get(0).getId());
        assertEquals(testDtoExclusionRule.getName(), actual.get(0).getName());
        assertEquals(testDtoExclusionRule.getType(), actual.get(0).getType());
        assertEquals(testDtoExclusionRule.getValue(), actual.get(0).getValue());
    }

    @Test
    void removeExclusionRule() {
        Long id = dao.create(testDtoExclusionRule);

        dao.remove(id);

        MailingExclusionRule actual = dao.get(id);
        assertNull(actual);
    }
}
