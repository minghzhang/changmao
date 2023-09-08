package com.louis.compress_decompress.impl;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import org.junit.Test;

/**
 * @Date:09/07/2023 11:16
 * @Description:
 */
public class ZipCompressorTest {

    ZipCompressor zipCompressor = new ZipCompressor();

    @Test
    public void compress() throws IOException {
        String destPath = "/Users/landon.zhang/Documents/SourceCode/opensource/changmao/zip/archive.zip";
        File file = new File(destPath);
        if (file.exists()) {
            file.delete();
        }
        zipCompressor.compress("/Users/landon.zhang/Documents/SourceCode/opensource/changmao/src", destPath);
    }

    @Test
    public void decompress() throws IOException {
        String destDirPath = "/Users/landon.zhang/Documents/SourceCode/opensource/changmao/unzip";
        deleteFile(new File(destDirPath));
        zipCompressor.decompress("/Users/landon.zhang/Documents/SourceCode/opensource/changmao/zip/archive.zip",
                destDirPath);
    }

    void deleteFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            Arrays.stream(files).forEach(f -> deleteFile(f));
        } else {
            file.delete();
        }
    }
}