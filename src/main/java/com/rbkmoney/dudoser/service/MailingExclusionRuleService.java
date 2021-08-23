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

    public MailingExclusionRule createExclusionRule(MailingExclusionRule messageExclusionRule) {
        if (dao.getByType(messageExclusionRule.getType()).isEmpty()) {
            Long id = dao.create(messageExclusionRule);
            messageExclusionRule.setId(id);
            return messageExclusionRule;
        } else {
            throw new IllegalStateException(
                    "Exclusion rule with type " + messageExclusionRule.getType() + "already exists"
            );
        }
    }

    public MailingExclusionRule getExclusionRule(Long id) {
        return dao.get(id);
    }

    public List<MailingExclusionRule> getExclusionRules(MailingExclusionRuleType type) {
        return dao.getByType(type);
    }

    public List<MailingExclusionRule> getExclusionRulesByShopId(String shopId) {
        return dao.getByShopId(shopId);
    }

    public void removeExclusionRule(Long id) {
        dao.remove(id);
    }
}
