package com.louis.translate;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @date : 2021/9/24
 */
public class GoogleTranslateTest {

    @Test
    public void translateText() throws Exception {
        GoogleTranslate instance = GoogleTranslate.getInstance();
        String value = instance.translateText("主要是美语中的弱读，例子丰富，且前后连贯，值得认真学习", "auto", "en");
        System.out.println(value);
    }
}