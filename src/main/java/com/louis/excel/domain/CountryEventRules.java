package com.louis.excel.domain;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

@Slf4j
@Data
public class CountryEventRules {

    private String country;

    private EventRules taxEventRules;

    private EventRules invoiceEventRules;

    public void checkRuleItemValue() {
        log.info("checkRuleItemValue begin");
        if (invoiceEventRules == null) {
            return;
        }

        EventRuleItems eventRuleItems = invoiceEventRules.getEventRuleItems();
        if (eventRuleItems == null) {
            return;
        }

        boolean liveEventRuleItemCheckedPassed = checkRuleItemValue(eventRuleItems.getLiveEventRuleItems());
        log.info("checkRuleItemValue liveEventRuleItemCheckedPassed: {}", liveEventRuleItemCheckedPassed);

        boolean recordedEventRuleItemCheckedPassed = checkRuleItemValue(eventRuleItems.getRecordedEventRuleItems());
        log.info("checkRuleItemValue recordedEventRuleItemCheckedPassed: {}", recordedEventRuleItemCheckedPassed);

        boolean inPersonEventRuleItemCheckedPassed = checkRuleItemValue(eventRuleItems.getInPersonEventRuleItems());
        log.info("checkRuleItemValue inPersonEventRuleItemCheckedPassed: {}", inPersonEventRuleItemCheckedPassed);

        boolean webinarRuleItemCheckedPassed = checkRuleItemValue(eventRuleItems.getWebinarRuleItems());
        log.info("checkRuleItemValue webinarRuleItemCheckedPassed: {}", webinarRuleItemCheckedPassed);

        log.info("checkRuleItemValue end");
    }

    private boolean checkRuleItemValue(List<RuleItem> ruleItems) {
        if (CollectionUtils.isEmpty(ruleItems)) {
            return true;
        }
        boolean allRuleItemsPassed = true;
        for (RuleItem ruleItem : ruleItems) {
            boolean checkRuleItemValueYOrNPassed = ruleItem.checkRuleItemValueYOrNPassed();
            if (!checkRuleItemValueYOrNPassed) {
                log.warn("the rule value don't catch, ruleItem: {}", ruleItem);
                allRuleItemsPassed = false;
            }
        }
        return allRuleItemsPassed;
    }
}
