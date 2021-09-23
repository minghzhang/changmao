package com.louis.pdf;

import org.junit.Test;

/**
 * @date : 2021/9/23
 */
public class PdfUtilsTest {

    @Test
    public void pdfToImage() {
        PdfUtils.pdfToImage("/Users/landon.zhang/Documents/SourceCode/books/Docker开发指南_14.pdf",
                "/Users/landon.zhang/Documents/SourceCode/books/images/");
    }
}