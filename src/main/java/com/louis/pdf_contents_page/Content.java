package com.louis.pdf_contents_page;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Date:09/06/2023 14:07
 * @Description:
 */
@Data
public class Content {

    private List<ContentItem> item;

    @Data
    public static class ContentItem {

        private SubContentItem parentItem;
        private List<SubContentItem> contentItems;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SubContentItem {

        private String title;
        private int pageNumber;
    }
}
