package com.rbkmoney.dudoser.dao;

import com.rbkmoney.damsel.message_sender.ExclusionType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum MailingExclusionRuleType {
    SHOP("shop");

    private final String code;

    public static MailingExclusionRuleType fromCode(String code) {
        return Arrays.stream(values())
                .filter(value -> value.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }

    public static MailingExclusionRuleType fromDamsel(ExclusionType type) {
        if (type.getValue() == 0) {
            return SHOP;
        } else {
            throw new IllegalArgumentException("Exclusion type " + type + " is not supported yet");
        }
    }
}
