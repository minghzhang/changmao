package com.louis.excel;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class TaxConstants {

    public static Set<String> tagSet = new HashSet<>();


    public static final String LIVE_EVENT_RULE_TAG = "Live event rules";

    public static final String RECORDED_EVENT_RULE_TAG = "Recorded event rules";
    public static final String IN_PERSON_EVENT_RULE_TAG = "In-Person Event Rules";
    public static final String WEBINAR_EVENT_RULE_TAG = "Webinar Rules";

    public static final int LIVE_EVENT_RULE_TAG_START_INDEX = 8;
    public static final int RECORDED_EVENT_RULE_TAG_START_INDEX = 21;
    public static final int IN_PERSON_EVENT_RULE_TAG_START_INDEX = 34;
    public static final int WEBINAR_EVENT_RULE_TAG_START_INDEX = 47;

    static {
        tagSet.add(LIVE_EVENT_RULE_TAG);
        tagSet.add(RECORDED_EVENT_RULE_TAG);
        tagSet.add(IN_PERSON_EVENT_RULE_TAG);
        tagSet.add(WEBINAR_EVENT_RULE_TAG);
    }

    public static boolean containsTag(String tag) {
        return tagSet.contains(tag);
    }

    public static boolean containsLiveEventRule() {
        return tagSet.contains(LIVE_EVENT_RULE_TAG);
    }

    public static boolean containsRecordedEventRule() {
        return tagSet.contains(RECORDED_EVENT_RULE_TAG);
    }

    public static boolean containsInPersonEventRule() {
        return tagSet.contains(IN_PERSON_EVENT_RULE_TAG);
    }

    public static boolean containsWebinarEventRule() {
        return tagSet.contains(WEBINAR_EVENT_RULE_TAG);
    }
}
