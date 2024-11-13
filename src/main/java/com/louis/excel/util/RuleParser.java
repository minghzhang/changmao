package com.louis.excel.util;

import com.louis.excel.domain.InvoiceItem;
import com.louis.excel.domain.TicketOnOfflineTypeEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RuleParser {

    //B2C Host in country and B2C attendee in country
    //B2C Host in country and B2B attendee in country
    //B2B host in country and B2C attendee in country
    //B2B host in country and B2B attendee in country
    //B2C Host in country and B2C attendee not in country
    //B2C Host in country and B2B attendee not in country
    //B2B host in country and B2C attendee not in country
    //B2B host in country and B2B attendee not in country
    //B2C Host not in country and B2C attendee in country
    //B2C Host not in country and B2B attendee in country
    //B2B host not in country and B2C attendee in country
    //B2B host not in country and B2B attendee in country

    /**
     * B2B ⇒ taxid_valid=1
     * B2C ⇒ taxid_valid=0
     * <p>
     * B2CHost  ⇒ host_taxid_valid=0
     * B2BHost  ⇒ host_taxid_valid=1
     * B2CAttendee ⇒ attendee_taxid_valid=0
     * B2BAttendee ⇒ attendee_taxid=1
     * <p>
     * Tag:
     * Live event rules
     * Recorded event rules
     * In-Person Event Rules
     * Webinar Rules
     */
    public static List<InvoiceItem> transferToInvoiceItems(String rule, String value, String countryCode, String tag) {
        rule = rule.toLowerCase();
        if (StringUtils.equalsIgnoreCase(value, "N")) {
            return Collections.EMPTY_LIST;
        }
        List<InvoiceItem> invoiceItems = new ArrayList<>();

        // 解析规则
        boolean isHostB2B = rule.contains("b2b host");
        boolean isAttendeeB2B = rule.contains("b2b attendee");
        boolean isHostInCountry = rule.contains("host in country");
        boolean isAttendeeInCountry = rule.contains("attendee in country");
        boolean isHostNotInCountry = rule.contains("host not in country");
        boolean isAttendeeNotInCountry = rule.contains("attendee not in country");

        TicketOnOfflineTypeEnum ticketOnOfflineType = parseTag(tag);

        // 创建第一个 InvoiceItem 实例
        InvoiceItem item1 = new InvoiceItem();
        item1.setHostTaxIdValid(isHostB2B ? 1 : 0);
        item1.setAttendeeTaxIdValid(isAttendeeB2B ? 1 : 0);
        item1.setHostCountryCode(isHostInCountry ? countryCode : "");
        item1.setAttendeeCountryCode(isAttendeeInCountry ? countryCode : "");
        item1.setAttendeeInvoiceEligible(1);
        item1.setTicketOnOfflineType(ticketOnOfflineType.getCode());
        invoiceItems.add(item1);

        // 如果存在 "not in country"，则创建第二个 InvoiceItem 实例
        if (isHostNotInCountry || isAttendeeNotInCountry) {
            InvoiceItem item2 = new InvoiceItem();
            item2.setHostTaxIdValid(isHostB2B ? 1 : 0);
            item2.setAttendeeTaxIdValid(isAttendeeB2B ? 1 : 0);
            item2.setHostCountryCode(countryCode);
            item2.setAttendeeCountryCode(countryCode);  // 始终设置为指定的 countryCode
            item2.setAttendeeInvoiceEligible(0);
            item2.setTicketOnOfflineType(ticketOnOfflineType.getCode());
            invoiceItems.add(item2);
        }

        return invoiceItems;
    }

    public static TicketOnOfflineTypeEnum parseTag(String tag) {
        tag = tag.toLowerCase();
        if (tag.contains("live")) {
            return TicketOnOfflineTypeEnum.VIRTUAL;
        }

        if (tag.contains("in-person")) {
            return TicketOnOfflineTypeEnum.IN_PERSON;
        }

        //todo
        if (tag.contains("recorded")) {
            return TicketOnOfflineTypeEnum.VIRTUAL;
        }
        //todo
        if (tag.contains("webinar")) {
            return TicketOnOfflineTypeEnum.VIRTUAL;
        }
        return null;
    }
}
