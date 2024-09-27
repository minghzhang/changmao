package com.louis.excel.domain;

import lombok.Data;

@Data
public class InvoiceItem {
    /**
     * B2CHost  ⇒ host_taxid_valid=0
     * B2BHost  ⇒ host_taxid_valid=1
     * B2CAttendee ⇒ attendee_taxid_valid=0
     * B2BAttendee ⇒ attendee_taxid=1
     * <p>
     * 如果报税国家为AU,
     * attendee IN country ⇒ attendee_country_code = 'AU'
     * host IN country = host_country_code='AU'
     * attendee_invoice_eligible 1 ⇒ YES, 0 ⇒ NO
     * host_invoice_eligible 全都是0
     * <p>
     * 15  B2C Host not in country and B2C attendee in country   Y
     * record 1：
     * host_taxid_valid=0
     * attendee_taxid_valid=0
     * attendee_country_code=IN
     * host_country_code=''
     * attendee_invoice_eligble=1
     */

    private int hostTaxIdValid;

    private int attendeeTaxIdValid;

    private String attendeeCountryCode;

    private String hostCountryCode;

    private int attendeeInvoiceEligible;

    private int hostInvoiceEligible = 0;

    //0:Virtual 1:In-Person 2:Hybrid 100:All
    private int ticketOnOfflineType;
}
