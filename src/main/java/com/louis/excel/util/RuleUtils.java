package com.louis.excel.util;

import com.louis.excel.TaxConstants;
import org.apache.commons.lang3.StringUtils;

public class RuleUtils {

    public static boolean isInLiveEventRuleRange(int rowNum) {
        return rowNum > TaxConstants.LIVE_EVENT_RULE_TAG_START_INDEX && rowNum < TaxConstants.RECORDED_EVENT_RULE_TAG_START_INDEX;
    }

    public static boolean isInRecordedEventRuleRange(int rowNum) {
        return rowNum > TaxConstants.RECORDED_EVENT_RULE_TAG_START_INDEX && rowNum < TaxConstants.IN_PERSON_EVENT_RULE_TAG_START_INDEX;
    }

    public static boolean isInPersonEventRuleRange(int rowNum) {
        return rowNum > TaxConstants.IN_PERSON_EVENT_RULE_TAG_START_INDEX && rowNum < TaxConstants.WEBINAR_EVENT_RULE_TAG_START_INDEX;
    }

    public static boolean isInWebinarRuleRange(int rowNum) {
        return rowNum > TaxConstants.WEBINAR_EVENT_RULE_TAG_START_INDEX;
    }

    public static boolean isLiveEventRule(String tag) {
        return StringUtils.equalsIgnoreCase(TaxConstants.LIVE_EVENT_RULE_TAG, tag);
    }

    public static boolean isRecordedEventRule(String tag) {
        return StringUtils.equalsIgnoreCase(TaxConstants.RECORDED_EVENT_RULE_TAG, tag);
    }

    public static boolean isInPersonEventRule(String tag) {
        return StringUtils.equalsIgnoreCase(TaxConstants.IN_PERSON_EVENT_RULE_TAG, tag);
    }

    public static boolean isWebinarEventRule(String tag) {
        return StringUtils.equalsIgnoreCase(TaxConstants.WEBINAR_EVENT_RULE_TAG, tag);
    }
}
