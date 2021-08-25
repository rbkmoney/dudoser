package com.rbkmoney.dudoser.converter;

import com.rbkmoney.damsel.message_sender.MessageExclusionRule;
import com.rbkmoney.damsel.message_sender.ShopExclusionRule;
import com.rbkmoney.dudoser.dao.MailingExclusionRule;
import com.rbkmoney.dudoser.dao.MailingExclusionRuleType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MailingExclusionRuleConverterTest {

    private final MailingExclusionRuleConverter converter = new MailingExclusionRuleConverter();

    private MessageExclusionRule testDamselExclusionRule;
    private MailingExclusionRule testDtoExclusionRule;

    @BeforeAll
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
        converter.fromThrift(testDamselExclusionRule, actual);
        assertEquals(testDtoExclusionRule, actual);
    }

    @Test
    void shouldSuccessfullyConvertDtoToDamsel() {
        MessageExclusionRule actual = new MessageExclusionRule();
        converter.toThrift(testDtoExclusionRule, actual);
        assertEquals(testDamselExclusionRule, actual);
    }

    @Test
    void shouldFailBecauseOfUnsupportedDamselRuleType() {
        assertThrows(IllegalArgumentException.class, () -> {
            converter.fromThrift(new MessageExclusionRule(), new MailingExclusionRule());
        });
    }

    @Test
    void shouldFailBecauseOfUnsupportedDtoRuleType() {
        assertThrows(IllegalArgumentException.class, () -> {
            converter.toThrift(new MailingExclusionRule(), new MessageExclusionRule());
        });
    }
}
