package com.louis.excel.domain;

import com.louis.excel.util.IdGenerator;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

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


    public String generateInsertSql() {
        // 生成 UUID 作为 id
        String id = IdGenerator.uuid();
        // 获取当前时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = dateFormat.format(new Date());

        String attendeeRegionCode = ""; // 空字符串
        String hostRegionCode = ""; // 空字符串

        // 生成 SQL 语句
        return String.format("INSERT INTO zm_billing_tax_invoice_rules (" +
                        "id, attendee_taxid_valid, host_taxid_valid, attendee_country_code, attendee_region_code, " +
                        "host_country_code, host_region_code, attendee_invoice_eligible, host_invoice_eligible, " +
                        "ticket_on_offline_type, create_time, modified_time) VALUES (" +
                        "'%s', %d, %d, '%s', '%s', '%s', '%s', %d, %d, %d, '%s', '%s');",
                id, attendeeTaxIdValid, hostTaxIdValid, attendeeCountryCode, attendeeRegionCode,
                hostCountryCode, hostRegionCode, attendeeInvoiceEligible, hostInvoiceEligible,
                ticketOnOfflineType, currentTime, currentTime);
    }
}
