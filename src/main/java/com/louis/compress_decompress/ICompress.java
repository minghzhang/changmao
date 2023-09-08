package com.louis.compress_decompress;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @Date:09/07/2023 11:13
 * @Description:
 */
public interface ICompress {

    void compress(String sourceDir, String targetZipFilePath) throws IOException;

    void decompress(String targetZipFilePath, String destDir) throws IOException;
}
