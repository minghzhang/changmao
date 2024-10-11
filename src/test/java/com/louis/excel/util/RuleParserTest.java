package com.louis.excel.util;

import com.louis.excel.domain.InvoiceItem;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class RuleParserTest {

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
     * B2CHost  ⇒ host_taxid_valid=0
     * B2BHost  ⇒ host_taxid_valid=1
     * B2CAttendee ⇒ attendee_taxid_valid=0
     * B2BAttendee ⇒ attendee_taxid=1
     */
    @Test
    public void transferToInvoiceItem() {
        List<InvoiceItem> invoiceItems = RuleParser.transferToInvoiceItems("B2C Host in country and B2C attendee in country", "Y", "AU");
        Assert.assertEquals(invoiceItems.size(), 1);
        Assert.assertEquals(0, invoiceItems.get(0).getHostTaxIdValid());
        Assert.assertEquals(0, invoiceItems.get(0).getAttendeeTaxIdValid());

        invoiceItems = RuleParser.transferToInvoiceItems("B2C Host in country and B2B attendee in country", "Y", "AU");
        Assert.assertEquals(invoiceItems.size(), 1);
        Assert.assertEquals(0, invoiceItems.get(0).getHostTaxIdValid());
        Assert.assertEquals(1, invoiceItems.get(0).getAttendeeTaxIdValid());

        invoiceItems = RuleParser.transferToInvoiceItems("B2B host in country and B2C attendee in country", "Y", "AU");
        Assert.assertEquals(invoiceItems.size(), 1);
        Assert.assertEquals(1, invoiceItems.get(0).getHostTaxIdValid());
        Assert.assertEquals(0, invoiceItems.get(0).getAttendeeTaxIdValid());

        invoiceItems = RuleParser.transferToInvoiceItems("B2B host in country and B2B attendee in country", "Y", "AU");
        Assert.assertEquals(invoiceItems.size(), 1);
        Assert.assertEquals(invoiceItems.get(0).getHostTaxIdValid(), 1);
        Assert.assertEquals(invoiceItems.get(0).getAttendeeTaxIdValid(), 1);

        invoiceItems = RuleParser.transferToInvoiceItems("B2C Host in country and B2C attendee not in country", "Y", "AU");
        Assert.assertEquals(invoiceItems.size(), 2);
        InvoiceItem firstItem = invoiceItems.stream().filter(item -> StringUtils.equalsIgnoreCase(item.getAttendeeCountryCode(), "")).findFirst().get();
        Assert.assertEquals(firstItem.getHostTaxIdValid(), 0);
        Assert.assertEquals(firstItem.getAttendeeTaxIdValid(), 0);
        Assert.assertEquals(firstItem.getAttendeeCountryCode(), "");
        Assert.assertEquals(firstItem.getHostCountryCode(), "AU");
        Assert.assertEquals(firstItem.getAttendeeInvoiceEligible(), 1);

        invoiceItems = RuleParser.transferToInvoiceItems("B2C Host in country and B2C attendee not in country", "Y", "AU");
        InvoiceItem secondItem = invoiceItems.stream().filter(item -> StringUtils.equalsIgnoreCase(item.getAttendeeCountryCode(), "AU")).findFirst().get();
        Assert.assertEquals(secondItem.getHostTaxIdValid(), 0);
        Assert.assertEquals(secondItem.getAttendeeTaxIdValid(), 0);
        Assert.assertEquals(secondItem.getAttendeeCountryCode(), "AU");
        Assert.assertEquals(secondItem.getHostCountryCode(), "AU");
        Assert.assertEquals(secondItem.getAttendeeInvoiceEligible(), 0);
    }
}