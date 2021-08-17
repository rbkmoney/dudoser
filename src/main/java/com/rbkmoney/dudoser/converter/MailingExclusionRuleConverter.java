package com.rbkmoney.dudoser.converter;

import com.rbkmoney.damsel.message_sender.MessageExclusionRule;
import com.rbkmoney.damsel.message_sender.ShopExclusionRule;
import com.rbkmoney.dudoser.dao.MailingExclusionRule;
import com.rbkmoney.dudoser.dao.MailingExclusionRuleType;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class MailingExclusionRuleConverter {

    private static final String separator = ",";

    public void convert(MessageExclusionRule from, MailingExclusionRule to) {
        if (from.isSetShopRule()) {
            to.setType(MailingExclusionRuleType.SHOP);
            String value = String.join(separator, from.getShopRule().shop_ids);
            to.setValue(value);
        } else {
            throw new IllegalArgumentException("Rule type " + from + " has not supported yet");
        }
    }

    public void convert(MailingExclusionRule from, MessageExclusionRule to) {
        if (MailingExclusionRuleType.SHOP.equals(from.getType())) {
            to.setShopRule(new ShopExclusionRule(Arrays.asList(from.getValue().split(separator))));
        } else {
            throw new IllegalArgumentException("Rule type " + from.getType() + " has not supported yet");
        }
    }
}
