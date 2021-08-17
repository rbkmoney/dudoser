package com.rbkmoney.dudoser.dao;

import java.util.List;

public interface MailingExclusionRuleDao {

    Long createExclusionRule(MailingExclusionRule messageExclusionRule);

    MailingExclusionRule getExclusionRule(Long id);

    List<MailingExclusionRule> getExclusionRules(MailingExclusionRuleType type);

    void removeExclusionRule(Long id);
}
