package com.louis.pdf;

import java.io.File;
import java.util.List;
import org.apache.commons.compress.utils.Lists;

public class Demo {

    public static void main(String[] args) {
        List<File> files = Lists.newArrayList();
        files.add(new File("/Users/landon.zhang/Downloads/Non-Immigrant Visa - Review Personal, Address, Phone, and Passport Information_ShuyunZhang_1.pdf"));
        files.add(new File("/Users/landon.zhang/Downloads/Non-Immigrant Visa - Review Travel Information_ShuyunZhang_2.pdf"));
        files.add(new File("/Users/landon.zhang/Downloads/Nonimmigrant Visa - Review US Contact Information_ShuyunZhang_3.pdf"));
        files.add(new File("/Users/landon.zhang/Downloads/Nonimmigrant Visa - Review Family Information_ShuyunZhang_4.pdf"));
        files.add(new File("/Users/landon.zhang/Downloads/Nonimmigrant Visa - Review Security Information_ShuyunZhang_5.pdf"));
        files.add(new File("/Users/landon.zhang/Downloads/Non-Immigrant Visa - Review Location_ShuyunZhang_6.pdf"));
        PdfUtils.pdfMerge(files,"mergeDestinationShuyunZhang.pdf");
    }
}
