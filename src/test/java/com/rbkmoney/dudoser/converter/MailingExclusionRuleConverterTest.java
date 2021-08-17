package com.rbkmoney.dudoser.converter;

import com.rbkmoney.damsel.message_sender.MessageExclusionRule;
import com.rbkmoney.damsel.message_sender.ShopExclusionRule;
import com.rbkmoney.dudoser.dao.MailingExclusionRule;
import com.rbkmoney.dudoser.dao.MailingExclusionRuleType;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MailingExclusionRuleConverterTest {

    private final MailingExclusionRuleConverter converter = new MailingExclusionRuleConverter();

    private MessageExclusionRule testDamselExclusionRule;
    private MailingExclusionRule testDtoExclusionRule;

    @Before
    void setup() {
        testDamselExclusionRule = new MessageExclusionRule();
        testDtoExclusionRule = new MailingExclusionRule();
        testDamselExclusionRule.setShopRule(new ShopExclusionRule(List.of("1", "2", "3")));
        testDtoExclusionRule.setType(MailingExclusionRuleType.SHOP);
        testDtoExclusionRule.setValue("1,2,3");
    }


    @Test
    void shouldSuccessfullyConvertDamselToDto() {
        MailingExclusionRule actual = new MailingExclusionRule();
        converter.convert(testDamselExclusionRule, actual);
        assertEquals(testDtoExclusionRule, actual);
    }

    @Test
    void shouldSuccessfullyConvertDtoToDamsel() {
        MessageExclusionRule actual = new MessageExclusionRule();
        converter.convert(testDtoExclusionRule, actual);
        assertEquals(testDamselExclusionRule, actual);
    }

    @Test
    void shouldFailBecauseOfUnsupportedDamselRuleType() {
        assertThrows(IllegalArgumentException.class, () -> {
            converter.convert(new MessageExclusionRule(), new MailingExclusionRule());
        });
    }

    @Test
    void shouldFailBecauseOfUnsupportedDtoRuleType() {
        assertThrows(IllegalArgumentException.class, () -> {
            converter.convert(new MailingExclusionRule(), new MessageExclusionRule());
        });
    }
}
