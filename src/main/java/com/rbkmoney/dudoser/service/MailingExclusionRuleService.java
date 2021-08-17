package com.rbkmoney.dudoser.service;

import com.rbkmoney.dudoser.dao.MailingExclusionRule;
import com.rbkmoney.dudoser.dao.MailingExclusionRuleDao;
import com.rbkmoney.dudoser.dao.MailingExclusionRuleType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MailingExclusionRuleService {

    private final MailingExclusionRuleDao dao;

    public void createExclusionRule(MailingExclusionRule messageExclusionRule) {
        if (dao.getExclusionRulesByType(messageExclusionRule.getType()).isEmpty()) {
            dao.createExclusionRule(messageExclusionRule);
        } else {
            throw new IllegalStateException(
                    "Exclusion rule with type " + messageExclusionRule.getType() + "already exists"
            );
        }
    }

    public MailingExclusionRule getExclusionRule(Long id) {
        return dao.getExclusionRule(id);
    }

    public List<MailingExclusionRule> getExclusionRules(MailingExclusionRuleType type) {
        return dao.getExclusionRulesByType(type);
    }

    public List<MailingExclusionRule> getExclusionRulesByShopId(String shopId) {
        return dao.getExclusionRulesByShopId(shopId);
    }

    public void removeExclusionRule(Long id) {
        dao.removeExclusionRule(id);
    }
}
