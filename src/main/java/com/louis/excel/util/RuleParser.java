package com.louis.excel.util;

import com.louis.excel.domain.InvoiceItem;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
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
     *
     */
    public static List<InvoiceItem> transferToInvoiceItems(String rule, String value, String countryCode) {
        if (StringUtils.equalsIgnoreCase(value, "N")) {
            return Collections.EMPTY_LIST;
        }
        /**
         *      B2B ⇒ taxid_valid=1
         *      B2C ⇒ taxid_valid=0
         *
         *      B2CHost  ⇒ host_taxid_valid=0
         *      B2BHost  ⇒ host_taxid_valid=1
         *      B2CAttendee ⇒ attendee_taxid_valid=0
         *      B2BAttendee ⇒ attendee_taxid=1
         */

        List<InvoiceItem> invoiceItemList = Lists.newArrayList();

        InvoiceItem invoiceItem = new InvoiceItem();
        String[] ruleItems = rule.split(" and ");
        String hostRuleItem = ruleItems[0];
        String attendeeRuleItem = ruleItems[1];

        String[] hostInCountries = hostRuleItem.split("in country");
        boolean hostIsB2C = StringUtils.containsIgnoreCase(hostInCountries[0], "B2C Host");
        boolean hostIsB2B = StringUtils.containsIgnoreCase(hostInCountries[0], "B2B Host");

        boolean hostIsNotInCountry = StringUtils.containsIgnoreCase(hostInCountries[0], "not");
        if (!hostIsNotInCountry) {
            invoiceItem.setHostCountryCode(countryCode);
        } else {
            invoiceItem.setHostCountryCode("");
        }

        String[] attendeeInCountries = attendeeRuleItem.split("in country");
        boolean attendeeIsB2C = StringUtils.containsIgnoreCase(attendeeInCountries[0], "B2C attendee");
        boolean attendeeIsB2B = StringUtils.containsIgnoreCase(attendeeInCountries[0], "B2B attendee");

        boolean attendeeIsNotInCountry = StringUtils.containsIgnoreCase(attendeeInCountries[0], "not");
        if (!attendeeIsNotInCountry) {
            invoiceItem.setAttendeeCountryCode(countryCode);
        } else {
            invoiceItem.setAttendeeCountryCode("");
        }
        invoiceItem.setHostTaxIdValid(hostIsB2C ? 0 : (hostIsB2B ? 1 : 0));
        invoiceItem.setAttendeeTaxIdValid(attendeeIsB2C ? 0 : (attendeeIsB2B ? 1 : 0));
        invoiceItem.setAttendeeInvoiceEligible(1);
        invoiceItemList.add(invoiceItem);

        if (hostIsNotInCountry || attendeeIsNotInCountry) {
            InvoiceItem newInvoiceItem = new InvoiceItem();
            try {
                BeanUtils.copyProperties(newInvoiceItem, invoiceItem);
                newInvoiceItem.setHostCountryCode(countryCode);
                newInvoiceItem.setAttendeeCountryCode(countryCode);
                newInvoiceItem.setAttendeeInvoiceEligible(0);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            invoiceItemList.add(newInvoiceItem);
        }

        return invoiceItemList;
    }
}
