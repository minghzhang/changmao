package com.louis.excel.domain;

import com.louis.excel.util.RuleParser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;

@Slf4j
@Data
@AllArgsConstructor
public class RuleItem {
    private String countryCode;

    private int rowNum;

    private String tag;

    private String key;

    private String value;

    public boolean checkRuleItemValueYOrNPassed() {
        return StringUtils.equalsIgnoreCase("Y", value) || StringUtils.equalsIgnoreCase("N", value);
    }

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

    public List<InvoiceItem> transferToInvoiceItems() {
        if (!checkRuleItemValueYOrNPassed()) {
            log.error("value content is unexpected, ruleItem: {}", this);
            return Collections.EMPTY_LIST;
        }
        return RuleParser.transferToInvoiceItems(key, value, countryCode, tag);
    }

    /**解析规则
     B2B ⇒ taxid_valid=1
     B2C ⇒ taxid_valid=0

     B2CHost  ⇒ host_taxid_valid=0
     B2BHost  ⇒ host_taxid_valid=1
     B2CAttendee ⇒ attendee_taxid_valid=0
     B2BAttendee ⇒ attendee_taxid=1

     如果报税国家为AU,
     attendee IN country ⇒ attendee_country_code = 'AU'
     host IN country = host_country_code='AU'
     attendee_invoice_eligible 1 ⇒ YES, 0 ⇒ NO
     host_invoice_eligible 全都是0

     15  B2C Host not in country and B2C attendee in country   Y
     record 1：
     host_taxid_valid=0
     attendee_taxid_valid=0
     attendee_country_code=IN
     host_country_code=''
     attendee_invoice_eligble=1

     record 2：
     7 B2CHost In country and B2C attendee in country   N
     host_taxid_valid=0
     attendee_taxid_valid=0
     attendee_country_code=IN
     host_country_code=IN
     attendee_invoice_eligble=0

     */

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
}
