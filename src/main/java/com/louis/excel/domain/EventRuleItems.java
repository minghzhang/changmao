package com.louis.excel.domain;

import com.louis.excel.TaxConstants;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Data
public class EventRuleItems {


    private List<RuleItem> liveEventRuleItems = new ArrayList<>();
    private List<RuleItem> recordedEventRuleItems = new ArrayList<>();
    private List<RuleItem> inPersonEventRuleItems = new ArrayList<>();
    private List<RuleItem> webinarRuleItems = new ArrayList<>();

    public void addRuleItem(RuleItem ruleItem) {
        String tag = ruleItem.getTag();
        switch (tag) {
            case TaxConstants.LIVE_EVENT_RULE_TAG -> liveEventRuleItems.add(ruleItem);
            case TaxConstants.RECORDED_EVENT_RULE_TAG -> recordedEventRuleItems.add(ruleItem);
            case TaxConstants.IN_PERSON_EVENT_RULE_TAG -> inPersonEventRuleItems.add(ruleItem);
            case TaxConstants.WEBINAR_EVENT_RULE_TAG -> webinarRuleItems.add(ruleItem);
            default -> log.error("tag is unexpected, tag: {}", tag);
        }
    }

    public boolean liveEventRuleItemsEqualsToRecordEventRuleItems() {
        if (CollectionUtils.size(liveEventRuleItems) != CollectionUtils.size(recordedEventRuleItems)) {
            log.info("size not match, liveEventRuleItems size: {},  recordedEventRuleItems.size: {}", liveEventRuleItems.size(), recordedEventRuleItems.size());
            return false;
        }

        int size = CollectionUtils.size(liveEventRuleItems);
        for (int i = 0; i < size; i++) {
            RuleItem liveEventRuleItem = liveEventRuleItems.get(i);
            RuleItem recordEventRuleItem = recordedEventRuleItems.get(i);
           /* log.info("liveEventRuleItem   index: {}, value: {}", i, liveEventRuleItem);
            log.info("recordEventRuleItem index: {}, value: {}", i, recordEventRuleItem);*/
            if (!liveEventRuleItem.equals(recordEventRuleItem)) {
                log.info("rule item not match, liveEventRuleItem: {}, recordEventRuleItem: {}", liveEventRuleItem, recordEventRuleItem);
                return false;
            }
        }

        return true;
    }

}
