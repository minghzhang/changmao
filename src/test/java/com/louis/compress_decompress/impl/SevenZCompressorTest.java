package com.louis.compress_decompress.impl;

import java.io.IOException;
import org.junit.Test;

/**
 * @Date:09/08/2023 10:09
 * @Description:
 */
public class SevenZCompressorTest {

    SevenZCompressor sevenZCompressor = new SevenZCompressor();

    @Test
    public void compressInternal() throws IOException {
        sevenZCompressor.compress("/Users/landon.zhang/Documents/SourceCode/opensource/changmao/src", "test.7z");
    }

    @Test
    public void decompressInternal() throws IOException {
        sevenZCompressor.decompress("test.7z", "un7z");
    }
}