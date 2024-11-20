package com.louis.excel;

import com.alibaba.fastjson2.JSON;
import com.louis.excel.domain.*;
import com.louis.excel.util.RuleUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Slf4j
public class ExcelParser {

    public static String excelFilePath = "/Users/landonzhang/IdeaProjects/opensource/changmao/src/main/java/com/louis/excel/tax_requirements.xlsx";

    public static void main(String[] args) {
        String newZealandCountry = "New Zealand invoice required - attendee ticket";
        CountryEventRules countryEventRules = getCountryEventRules(newZealandCountry, "NZ");
        EventRules invoiceEventRules = countryEventRules.getInvoiceEventRules();
        EventRuleItems eventRuleItems = invoiceEventRules.getEventRuleItems();
        List<RuleItem> liveEventRuleItems = eventRuleItems.getLiveEventRuleItems();
        for (RuleItem liveEventRuleItem : liveEventRuleItems) {
            String value = liveEventRuleItem.getValue();
            if (!StringUtils.equalsIgnoreCase("Y", value) && !StringUtils.equalsIgnoreCase("N", value)) {
                log.warn("the rule value don't catch, ruleItem: {}", liveEventRuleItem);
            }
        }


        EventRuleItems invoiceEventRuleItems = countryEventRules.getInvoiceEventRules().getEventRuleItems();
        for (RuleItem liveEventRuleItem : invoiceEventRuleItems.getLiveEventRuleItems()) {
            List<InvoiceItem> invoiceItemList = liveEventRuleItem.transferToInvoiceItems();
            if (CollectionUtils.isEmpty(invoiceItemList)) {
                continue;
            }
            log.info("live RuleItem：{}", JSON.toJSONString(liveEventRuleItem));
            log.info("live invoiceRules：{}", JSON.toJSONString(invoiceItemList));
            for (InvoiceItem invoiceItem : invoiceItemList) {
                String sql = invoiceItem.generateInsertSql();
                log.info("insertSql: {}", sql);
            }
        }

        boolean liveEventRuleItemsEqualsToRecordEventRuleItems = invoiceEventRuleItems.liveEventRuleItemsEqualsToRecordEventRuleItems();
        log.info("liveEventRuleItemsEqualsToRecordEventRuleItems : {}", liveEventRuleItemsEqualsToRecordEventRuleItems);
        if (!liveEventRuleItemsEqualsToRecordEventRuleItems) {
            for (RuleItem recordedEventRuleItem : invoiceEventRuleItems.getRecordedEventRuleItems()) {
                List<InvoiceItem> invoiceItemList = recordedEventRuleItem.transferToInvoiceItems();
                log.info("recorded invoiceRules：{}", JSON.toJSONString(invoiceItemList));
                for (InvoiceItem invoiceItem : invoiceItemList) {
                    String sql = invoiceItem.generateInsertSql();
                    log.info("insertSql: {}", sql);
                }
            }
        }

        for (RuleItem inPersonEventRuleItem : invoiceEventRuleItems.getInPersonEventRuleItems()) {
            List<InvoiceItem> invoiceItemList = inPersonEventRuleItem.transferToInvoiceItems();
            if (CollectionUtils.isEmpty(invoiceItemList)) {
                continue;
            }
            log.info("inperson invoiceRules：{}", JSON.toJSONString(invoiceItemList));
            for (InvoiceItem invoiceItem : invoiceItemList) {
                String sql = invoiceItem.generateInsertSql();
                log.info("insertSql: {}", sql);
            }
        }

       /* for (RuleItem webinarEventRuleItem : invoiceEventRuleItems.getWebinarRuleItems()) {
            List<InvoiceItem> invoiceItemList = webinarEventRuleItem.transferToInvoiceItems();
            log.info("webinar invoiceRules：{}", JSON.toJSONString(invoiceItemList));
            for (InvoiceItem invoiceItem : invoiceItemList) {
                String sql = invoiceItem.generateInsertSql();
                log.info("insertSql: {}", sql);
            }
        }*/


        // log.info("countryEventRules : {}", JSON.toJSONString(countryEventRules));
    }

    public static CountryEventRules getCountryEventRules(String countryNameForColumn, String countryCode) {
        List<CountryEventRules> parseEventRulesList = getParseEventRulesList(excelFilePath, countryCode);
        return parseEventRulesList.stream().filter(item -> item.getCountry().equals(countryNameForColumn))
                .findFirst().orElse(new CountryEventRules());
    }

    public static List<CountryEventRules> getParseEventRulesList(String excelFilePath, String countryCode) {
        List<CountryEventRules> countryEventRulesList = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // 获取第一个工作表

            Row firstRow = sheet.getRow(0); // 获取第一行
            Row secondRow = sheet.getRow(1); // 获取第二行

            // 创建一个Map来存储每个国家的属性值
            Map<String, Map<String, String>> countryData = new HashMap<>();
            CountryEventRules previousCountryEventRules = null;
            // 遍历每一列（从第二列开始，因为第一列是属性名称）
            for (int col = 1; col < firstRow.getLastCellNum(); col++) {
                if (previousCountryEventRules == null) {
                    previousCountryEventRules = new CountryEventRules();
                }
                String countryName = secondRow.getCell(col).getStringCellValue();
                previousCountryEventRules.setCountry(countryName);
                System.out.println(countryName);
                Map<String, String> attributes = new TreeMap<>();

                EventRuleItems eventRuleItems = new EventRuleItems();
                if (isTax(countryName)) {
                    EventRules taxEventRules = new EventRules();
                    taxEventRules.setEventRuleItems(eventRuleItems);
                    previousCountryEventRules.setTaxEventRules(taxEventRules);
                } else if (isInvoice(countryName)) {
                    EventRules invoiceEventRules = new EventRules();
                    invoiceEventRules.setEventRuleItems(eventRuleItems);
                    previousCountryEventRules.setInvoiceEventRules(invoiceEventRules);
                } else {
                    log.error("confusing countryName: {}", countryName);
                }

                // 遍历每一行（从第三行开始，因为前两行是表头）
                for (int row = 2; row <= sheet.getLastRowNum(); row++) {
                    Row currentRow = sheet.getRow(row);
                    if (currentRow != null) {
                        String attributeName = currentRow.getCell(0).getStringCellValue();
                        Cell valueCell = currentRow.getCell(col);
                        String attributeValue = valueCell != null ? valueCell.toString() : "";

                        attributes.put(attributeName, attributeValue);
                        // log.info("attributeName=" + attributeName + ", attributeValue=" + attributeValue);
                        int rowNum = currentRow.getRowNum();
                        int rowNumberWithPadding = rowNum + 1;
                        if (RuleUtils.isInLiveEventRuleRange(rowNum)) {
                            //  log.info("isLiveEventRule" + rowNum);
                            eventRuleItems.addRuleItem(new RuleItem(countryCode, rowNumberWithPadding, TaxConstants.LIVE_EVENT_RULE_TAG, attributeName, attributeValue));

                        } else if (RuleUtils.isInRecordedEventRuleRange(rowNum)) {
                            //   log.info("isRecordedEventRule" + rowNum);
                            eventRuleItems.addRuleItem(new RuleItem(countryCode, rowNumberWithPadding, TaxConstants.RECORDED_EVENT_RULE_TAG, attributeName, attributeValue));

                        } else if (RuleUtils.isInPersonEventRuleRange(rowNum)) {
                            //    log.info("isInPersonEventRule" + rowNum);
                            eventRuleItems.addRuleItem(new RuleItem(countryCode, rowNumberWithPadding, TaxConstants.IN_PERSON_EVENT_RULE_TAG, attributeName, attributeValue));

                        } else if (RuleUtils.isInWebinarRuleRange(rowNum)) {
                            //    log.info("isWebinarEventRule" + rowNum);
                            eventRuleItems.addRuleItem(new RuleItem(countryCode, rowNumberWithPadding, TaxConstants.WEBINAR_EVENT_RULE_TAG, attributeName, attributeValue));
                        }
                    }
                }

                if (previousCountryEventRules.getTaxEventRules() != null && previousCountryEventRules.getInvoiceEventRules() != null) {
                    countryEventRulesList.add(previousCountryEventRules);
                    previousCountryEventRules = null;
                }
                //log.info("countryName=" + countryName + ", attributes=" + attributes);
                countryData.put(countryName, attributes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return countryEventRulesList;
    }

    private static boolean isInvoice(String countryName) {
        return StringUtils.contains(countryName, "invoice")
                || StringUtils.contains(countryName, "attendee")
                || StringUtils.contains(countryName, "ticket");
    }

    private static boolean isTax(String countryName) {
        return !StringUtils.contains(countryName, "invoice")
                && !StringUtils.contains(countryName, "attendee")
                && !StringUtils.contains(countryName, "ticket");
    }


}