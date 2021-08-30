package com.rbkmoney.dudoser.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailingExclusionRule {
    private Long id;
    private String name;
    private MailingExclusionRuleType type;
    private String value;
}
