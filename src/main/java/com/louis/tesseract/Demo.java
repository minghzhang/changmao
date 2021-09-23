package com.louis.tesseract;

import java.io.IOException;

/**
 * @date : 2021/9/23
 */
public class Demo {

    public static void main(String[] args) throws IOException, InterruptedException {

        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(new String[]{"tesseract /Users/landon.zhang/Documents/SourceCode/image/ch1.jpg /Users/landon.zhang/Documents/SourceCode/image/output -l chi_sim"});
        int status = process.waitFor();
        if (status != 0) {
            System.out.println("failed to call shell's command.");
        }
    }
}
