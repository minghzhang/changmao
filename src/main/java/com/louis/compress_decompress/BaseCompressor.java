package com.louis.compress_decompress;

import com.google.common.base.Stopwatch;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

/**
 * @Date:09/07/2023 11:20
 * @Description:
 */
@Slf4j
public abstract class BaseCompressor implements ICompress {

    @Override
    public void compress(String sourceDir, String targetZipFilePath) throws IOException {
        Stopwatch stopWatch = Stopwatch.createStarted();
        compressInternal(sourceDir, targetZipFilePath);
        stopWatch.stop();
        log.info("compress, cost time: {}", stopWatch.toString());
    }

    @Override
    public void decompress(String targetZipFilePath, String destDir) throws IOException {
        Stopwatch stopWatch = Stopwatch.createStarted();
        decompressInternal(targetZipFilePath, destDir);
        stopWatch.stop();
        log.info("decompress, cost time: {}", stopWatch.toString());
    }

    protected abstract void compressInternal(String sourceDir, String zipFilePath) throws IOException;

    protected abstract void decompressInternal(String zipFilePath, String destDir) throws IOException;
}
