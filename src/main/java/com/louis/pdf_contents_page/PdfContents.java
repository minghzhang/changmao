package com.louis.pdf_contents_page;

import com.alibaba.fastjson2.JSON;
import com.louis.pdf_contents_page.Content.ContentItem;
import com.louis.pdf_contents_page.Content.SubContentItem;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.commons.codec.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageFitWidthDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;

/**
 * @Date:09/06/2023 13:57
 * @Description:
 */
public class PdfContents {


    public static void main(String[] args) throws IOException {
        PdfContents pdfContents = new PdfContents();

        Content content = getContent("ielts_speaking.json");
        String inputPdfPath = "/Users/landon.zhang/Downloads/会让你在IELTS写作与口语考试中更像一个Native.pdf";
        String outputPdfPath = "output_1.pdf";
        pdfContents.addTableContents(content, inputPdfPath, outputPdfPath);
    }

    private void addTableContents(Content content, String inputPdfPath, String outputPdfPath) {
        try {
            PDDocument document = PDDocument.load(new File(inputPdfPath));
            PDDocumentCatalog catalog = document.getDocumentCatalog();
            PDDocumentOutline outline = catalog.getDocumentOutline();
            if (outline == null) {
                outline = new PDDocumentOutline();
                catalog.setDocumentOutline(outline);
            }

            PDOutlineItem rootItem = new PDOutlineItem();
            rootItem.setTitle("目录");
            outline.addLast(rootItem);

            for (ContentItem contentItem : content.getItem()) {
                SubContentItem parentItem = contentItem.getParentItem();

                PDOutlineItem parentChapter = null;
                if (parentItem != null) {
                    parentChapter = createOutlineItem(parentItem.getTitle(), document, parentItem.getPageNumber());
                    rootItem.addLast(parentChapter);
                }

                List<SubContentItem> subContentItems = contentItem.getContentItems();
                if (subContentItems == null) {
                    continue;
                }
                for (SubContentItem subContentItem : subContentItems) {
                    PDOutlineItem subChapter = createOutlineItem(subContentItem.getTitle(), document, subContentItem.getPageNumber());
                    if (parentChapter != null) {
                        parentChapter.addLast(subChapter);
                    }
                }
            }

            document.save(new File(outputPdfPath));
            document.close();

            System.out.println("目录已成功添加到PDF文件中。");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Content getContent(String contentJsonName) throws IOException {
        String contentString = FileUtils.readFileToString(new File("json/" + contentJsonName),
                Charsets.UTF_8);
        Content content = JSON.parseObject(contentString, Content.class);
        System.out.println(JSON.toJSON(content));
        return content;
    }

    private static PDOutlineItem createOutlineItem(String title, PDDocument document, int pageIndex) {
        PDOutlineItem parentChapter = new PDOutlineItem();
        parentChapter.setTitle(title);
        PDPageFitWidthDestination dest = new PDPageFitWidthDestination();
        dest.setPage(document.getPage(pageIndex));
        parentChapter.setDestination(dest);
        return parentChapter;
    }
}
