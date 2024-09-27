package com.louis.excel.util;

import com.louis.excel.domain.InvoiceItem;

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
    public static InvoiceItem transferToInvoiceItem(String rule) {

        /**
         *      B2B ⇒ taxid_valid=1
         *      B2C ⇒ taxid_valid=0
         *
         *      B2CHost  ⇒ host_taxid_valid=0
         *      B2BHost  ⇒ host_taxid_valid=1
         *      B2CAttendee ⇒ attendee_taxid_valid=0
         *      B2BAttendee ⇒ attendee_taxid=1
         */

        InvoiceItem invoiceItem = new InvoiceItem();
        String[] ruleItems = rule.split(" and ");
        String hostRuleItem = ruleItems[0];
        String attendeeRuleItem = ruleItems[1];

        String[] hostInCountries = hostRuleItem.split("in country");
        boolean hostIsB2C = hostInCountries[0].contains("B2C Host");
        boolean hostIsB2B = hostInCountries[0].contains("B2B Host");

        String[] attendeeInCountries = attendeeRuleItem.split("in country");
        boolean attendeeIsB2C = attendeeInCountries[0].contains("B2C attendee");
        boolean attendeeIsB2B = attendeeInCountries[0].contains("B2B attendee");

        invoiceItem.setHostTaxIdValid(hostIsB2C ? 0 : (hostIsB2B ? 1 : 0));
        invoiceItem.setAttendeeTaxIdValid(attendeeIsB2C ? 0 : (attendeeIsB2B ? 1 : 0));

        return invoiceItem;
    }
}
