package com.rbkmoney.dudoser.dao;

import java.util.List;

public interface MailingExclusionRuleDao {

    Long createExclusionRule(MailingExclusionRule messageExclusionRule);

    MailingExclusionRule getExclusionRule(Long id);

    List<MailingExclusionRule> getExclusionRulesByType(MailingExclusionRuleType type);

    List<MailingExclusionRule> getExclusionRulesByShopId(String shopId);

    void removeExclusionRule(Long id);
}
