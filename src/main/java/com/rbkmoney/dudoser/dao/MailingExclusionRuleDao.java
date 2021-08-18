package com.rbkmoney.dudoser.dao;

import java.util.List;

public interface MailingExclusionRuleDao {

    Long create(MailingExclusionRule messageExclusionRule);

    MailingExclusionRule get(Long id);

    List<MailingExclusionRule> getByType(MailingExclusionRuleType type);

    List<MailingExclusionRule> getByShopId(String shopId);

    void remove(Long id);
}
