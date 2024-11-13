package com.louis.excel.util;

import com.louis.excel.domain.InvoiceItem;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

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


    private static final String COUNTRY_CODE = "IN";

    private static final String TAG = "Live event rules";

    /**
     * B2CHost  ⇒ host_taxid_valid=0
     * B2BHost  ⇒ host_taxid_valid=1
     * B2CAttendee ⇒ attendee_taxid_valid=0
     * B2BAttendee ⇒ attendee_taxid=1
     */
    @Test
    public void testTransferToInvoiceItems_B2C_Host_in_country_and_B2C_attendee_in_country() {
        String rule = "B2C Host in country and B2C attendee in country";
        List<InvoiceItem> result = RuleParser.transferToInvoiceItems(rule, "Y", COUNTRY_CODE, TAG);

        assertEquals(1, result.size());
        InvoiceItem item = result.get(0);
        assertEquals(0, item.getHostTaxIdValid());
        assertEquals(0, item.getAttendeeTaxIdValid());
        assertEquals(COUNTRY_CODE, item.getHostCountryCode());
        assertEquals(COUNTRY_CODE, item.getAttendeeCountryCode());
        assertEquals(1, item.getAttendeeInvoiceEligible());
    }

    /**
     * B2CHost  ⇒ host_taxid_valid=0
     * B2BHost  ⇒ host_taxid_valid=1
     * B2CAttendee ⇒ attendee_taxid_valid=0
     * B2BAttendee ⇒ attendee_taxid=1
     */
    @Test
    public void testTransferToInvoiceItems_B2C_Host_in_country_and_B2B_attendee_in_country() {
        String rule = "B2C Host in country and B2B attendee in country";
        List<InvoiceItem> result = RuleParser.transferToInvoiceItems(rule, "Y", COUNTRY_CODE, TAG);

        assertEquals(1, result.size());
        InvoiceItem item = result.get(0);
        assertEquals(0, item.getHostTaxIdValid());
        assertEquals(1, item.getAttendeeTaxIdValid());
        assertEquals(COUNTRY_CODE, item.getHostCountryCode());
        assertEquals(COUNTRY_CODE, item.getAttendeeCountryCode());
        assertEquals(1, item.getAttendeeInvoiceEligible());
    }

    /**
     * B2CHost  ⇒ host_taxid_valid=0
     * B2BHost  ⇒ host_taxid_valid=1
     * B2CAttendee ⇒ attendee_taxid_valid=0
     * B2BAttendee ⇒ attendee_taxid=1
     */
    @Test
    public void testTransferToInvoiceItems_B2B_host_in_country_and_B2C_attendee_in_country() {
        String rule = "B2B host in country and B2C attendee in country";
        List<InvoiceItem> result = RuleParser.transferToInvoiceItems(rule, "Y", COUNTRY_CODE, TAG);

        assertEquals(1, result.size());
        InvoiceItem item = result.get(0);
        assertEquals(1, item.getHostTaxIdValid());
        assertEquals(0, item.getAttendeeTaxIdValid());
        assertEquals(COUNTRY_CODE, item.getHostCountryCode());
        assertEquals(COUNTRY_CODE, item.getAttendeeCountryCode());
        assertEquals(1, item.getAttendeeInvoiceEligible());
    }

    /**
     * B2CHost  ⇒ host_taxid_valid=0
     * B2BHost  ⇒ host_taxid_valid=1
     * B2CAttendee ⇒ attendee_taxid_valid=0
     * B2BAttendee ⇒ attendee_taxid=1
     */
    @Test
    public void testTransferToInvoiceItems_B2B_host_in_country_and_B2B_attendee_in_country() {
        String rule = "B2B host in country and B2B attendee in country";
        List<InvoiceItem> result = RuleParser.transferToInvoiceItems(rule, "Y", COUNTRY_CODE, TAG);

        assertEquals(1, result.size());
        InvoiceItem item = result.get(0);
        assertEquals(1, item.getHostTaxIdValid());
        assertEquals(1, item.getAttendeeTaxIdValid());
        assertEquals(COUNTRY_CODE, item.getHostCountryCode());
        assertEquals(COUNTRY_CODE, item.getAttendeeCountryCode());
        assertEquals(1, item.getAttendeeInvoiceEligible());
    }

    /**
     * B2CHost  ⇒ host_taxid_valid=0
     * B2BHost  ⇒ host_taxid_valid=1
     * B2CAttendee ⇒ attendee_taxid_valid=0
     * B2BAttendee ⇒ attendee_taxid=1
     */
    @Test
    public void testTransferToInvoiceItems_B2C_Host_in_country_and_B2C_attendee_not_in_country() {
        String rule = "B2C Host in country and B2C attendee not in country";
        List<InvoiceItem> result = RuleParser.transferToInvoiceItems(rule, "Y", COUNTRY_CODE, TAG);

        assertEquals(2, result.size());

        InvoiceItem item1 = result.get(0);
        assertEquals(0, item1.getHostTaxIdValid());
        assertEquals(0, item1.getAttendeeTaxIdValid());
        assertEquals(COUNTRY_CODE, item1.getHostCountryCode());
        assertEquals("", item1.getAttendeeCountryCode());
        assertEquals(1, item1.getAttendeeInvoiceEligible());

        InvoiceItem item2 = result.get(1);
        assertEquals(0, item2.getHostTaxIdValid());
        assertEquals(0, item2.getAttendeeTaxIdValid());
        assertEquals(COUNTRY_CODE, item2.getHostCountryCode());
        assertEquals(COUNTRY_CODE, item2.getAttendeeCountryCode());
        assertEquals(0, item2.getAttendeeInvoiceEligible());
    }

    /**
     * B2CHost  ⇒ host_taxid_valid=0
     * B2BHost  ⇒ host_taxid_valid=1
     * B2CAttendee ⇒ attendee_taxid_valid=0
     * B2BAttendee ⇒ attendee_taxid=1
     */
    @Test
    public void testTransferToInvoiceItems_B2C_Host_in_country_and_B2B_attendee_not_in_country() {
        String rule = "B2C Host in country and B2B attendee not in country";
        List<InvoiceItem> result = RuleParser.transferToInvoiceItems(rule, "Y", COUNTRY_CODE, TAG);

        assertEquals(2, result.size());

        InvoiceItem item1 = result.get(0);
        assertEquals(0, item1.getHostTaxIdValid());
        assertEquals(1, item1.getAttendeeTaxIdValid());
        assertEquals(COUNTRY_CODE, item1.getHostCountryCode());
        assertEquals("", item1.getAttendeeCountryCode());
        assertEquals(1, item1.getAttendeeInvoiceEligible());

        InvoiceItem item2 = result.get(1);
        assertEquals(0, item2.getHostTaxIdValid());
        assertEquals(1, item2.getAttendeeTaxIdValid());
        assertEquals(COUNTRY_CODE, item2.getHostCountryCode());
        assertEquals(COUNTRY_CODE, item2.getAttendeeCountryCode());
        assertEquals(0, item2.getAttendeeInvoiceEligible());
    }

    /**
     * B2CHost  ⇒ host_taxid_valid=0
     * B2BHost  ⇒ host_taxid_valid=1
     * B2CAttendee ⇒ attendee_taxid_valid=0
     * B2BAttendee ⇒ attendee_taxid=1
     */
    @Test
    public void testTransferToInvoiceItems_B2B_host_in_country_and_B2C_attendee_not_in_country() {
        String rule = "B2B host in country and B2C attendee not in country";
        List<InvoiceItem> result = RuleParser.transferToInvoiceItems(rule, "Y", COUNTRY_CODE, TAG);

        assertEquals(2, result.size());

        InvoiceItem item1 = result.get(0);
        assertEquals(1, item1.getHostTaxIdValid());
        assertEquals(0, item1.getAttendeeTaxIdValid());
        assertEquals(COUNTRY_CODE, item1.getHostCountryCode());
        assertEquals("", item1.getAttendeeCountryCode());
        assertEquals(1, item1.getAttendeeInvoiceEligible());

        InvoiceItem item2 = result.get(1);
        assertEquals(1, item2.getHostTaxIdValid());
        assertEquals(0, item2.getAttendeeTaxIdValid());
        assertEquals(COUNTRY_CODE, item2.getHostCountryCode());
        assertEquals(COUNTRY_CODE, item2.getAttendeeCountryCode());
        assertEquals(0, item2.getAttendeeInvoiceEligible());
    }

    /**
     * B2CHost  ⇒ host_taxid_valid=0
     * B2BHost  ⇒ host_taxid_valid=1
     * B2CAttendee ⇒ attendee_taxid_valid=0
     * B2BAttendee ⇒ attendee_taxid=1
     */
    @Test
    public void testTransferToInvoiceItems_B2B_host_in_country_and_B2B_attendee_not_in_country() {
        String rule = "B2B host in country and B2B attendee not in country";
        List<InvoiceItem> result = RuleParser.transferToInvoiceItems(rule, "Y", COUNTRY_CODE, TAG);

        assertEquals(2, result.size());

        InvoiceItem item1 = result.get(0);
        assertEquals(1, item1.getHostTaxIdValid());
        assertEquals(1, item1.getAttendeeTaxIdValid());
        assertEquals(COUNTRY_CODE, item1.getHostCountryCode());
        assertEquals("", item1.getAttendeeCountryCode());
        assertEquals(1, item1.getAttendeeInvoiceEligible());

        InvoiceItem item2 = result.get(1);
        assertEquals(1, item2.getHostTaxIdValid());
        assertEquals(1, item2.getAttendeeTaxIdValid());
        assertEquals(COUNTRY_CODE, item2.getHostCountryCode());
        assertEquals(COUNTRY_CODE, item2.getAttendeeCountryCode());
        assertEquals(0, item2.getAttendeeInvoiceEligible());
    }

    /**
     * B2CHost  ⇒ host_taxid_valid=0
     * B2BHost  ⇒ host_taxid_valid=1
     * B2CAttendee ⇒ attendee_taxid_valid=0
     * B2BAttendee ⇒ attendee_taxid=1
     */
    @Test
    public void testTransferToInvoiceItems_B2C_Host_not_in_country_and_B2C_attendee_in_country() {
        String rule = "B2C Host not in country and B2C attendee in country";
        List<InvoiceItem> result = RuleParser.transferToInvoiceItems(rule, "Y", COUNTRY_CODE, TAG);

        assertEquals(2, result.size());

        InvoiceItem item1 = result.get(0);
        assertEquals(0, item1.getHostTaxIdValid());
        assertEquals(0, item1.getAttendeeTaxIdValid());
        assertEquals("", item1.getHostCountryCode());
        assertEquals(COUNTRY_CODE, item1.getAttendeeCountryCode());
        assertEquals(1, item1.getAttendeeInvoiceEligible());

        InvoiceItem item2 = result.get(1);
        assertEquals(0, item2.getHostTaxIdValid());
        assertEquals(0, item2.getAttendeeTaxIdValid());
        assertEquals(COUNTRY_CODE, item2.getHostCountryCode());
        assertEquals(COUNTRY_CODE, item2.getAttendeeCountryCode());
        assertEquals(0, item2.getAttendeeInvoiceEligible());
    }

    /**
     * B2CHost  ⇒ host_taxid_valid=0
     * B2BHost  ⇒ host_taxid_valid=1
     * B2CAttendee ⇒ attendee_taxid_valid=0
     * B2BAttendee ⇒ attendee_taxid=1
     */
    @Test
    public void testTransferToInvoiceItems_B2C_Host_not_in_country_and_B2B_attendee_in_country() {
        String rule = "B2C Host not in country and B2B attendee in country";
        List<InvoiceItem> result = RuleParser.transferToInvoiceItems(rule, "Y", COUNTRY_CODE, TAG);

        assertEquals(2, result.size());

        InvoiceItem item1 = result.get(0);
        assertEquals(0, item1.getHostTaxIdValid());
        assertEquals(1, item1.getAttendeeTaxIdValid());
        assertEquals("", item1.getHostCountryCode());
        assertEquals(COUNTRY_CODE, item1.getAttendeeCountryCode());
        assertEquals(1, item1.getAttendeeInvoiceEligible());

        InvoiceItem item2 = result.get(1);
        assertEquals(0, item2.getHostTaxIdValid());
        assertEquals(1, item2.getAttendeeTaxIdValid());
        assertEquals(COUNTRY_CODE, item2.getHostCountryCode());
        assertEquals(COUNTRY_CODE, item2.getAttendeeCountryCode());
        assertEquals(0, item2.getAttendeeInvoiceEligible());
    }

    /**
     * B2CHost  ⇒ host_taxid_valid=0
     * B2BHost  ⇒ host_taxid_valid=1
     * B2CAttendee ⇒ attendee_taxid_valid=0
     * B2BAttendee ⇒ attendee_taxid=1
     */
    @Test
    public void testTransferToInvoiceItems_B2B_host_not_in_country_and_B2C_attendee_in_country() {
        String rule = "B2B host not in country and B2C attendee in country";
        List<InvoiceItem> result = RuleParser.transferToInvoiceItems(rule, "Y", COUNTRY_CODE, TAG);

        assertEquals(2, result.size());

        InvoiceItem item1 = result.get(0);
        assertEquals(1, item1.getHostTaxIdValid());
        assertEquals(0, item1.getAttendeeTaxIdValid());
        assertEquals("", item1.getHostCountryCode());
        assertEquals(COUNTRY_CODE, item1.getAttendeeCountryCode());
        assertEquals(1, item1.getAttendeeInvoiceEligible());

        InvoiceItem item2 = result.get(1);
        assertEquals(1, item2.getHostTaxIdValid());
        assertEquals(0, item2.getAttendeeTaxIdValid());
        assertEquals(COUNTRY_CODE, item2.getHostCountryCode());
        assertEquals(COUNTRY_CODE, item2.getAttendeeCountryCode());
        assertEquals(0, item2.getAttendeeInvoiceEligible());
    }

    /**
     * B2CHost  ⇒ host_taxid_valid=0
     * B2BHost  ⇒ host_taxid_valid=1
     * B2CAttendee ⇒ attendee_taxid_valid=0
     * B2BAttendee ⇒ attendee_taxid=1
     */
    @Test
    public void testTransferToInvoiceItems_B2B_host_not_in_country_and_B2B_attendee_in_country() {
        String rule = "B2B host not in country and B2B attendee in country";
        List<InvoiceItem> result = RuleParser.transferToInvoiceItems(rule, "Y", COUNTRY_CODE, TAG);

        assertEquals(2, result.size());

        InvoiceItem item1 = result.get(0);
        assertEquals(1, item1.getHostTaxIdValid());
        assertEquals(1, item1.getAttendeeTaxIdValid());
        assertEquals("", item1.getHostCountryCode());
        assertEquals(COUNTRY_CODE, item1.getAttendeeCountryCode());
        assertEquals(1, item1.getAttendeeInvoiceEligible());

        InvoiceItem item2 = result.get(1);
        assertEquals(1, item2.getHostTaxIdValid());
        assertEquals(1, item2.getAttendeeTaxIdValid());
        assertEquals(COUNTRY_CODE, item2.getHostCountryCode());
        assertEquals(COUNTRY_CODE, item2.getAttendeeCountryCode());
        assertEquals(0, item2.getAttendeeInvoiceEligible());
    }
}