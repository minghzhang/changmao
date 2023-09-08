package com.louis.compress_decompress.impl;

import com.louis.compress_decompress.BaseCompressor;
import com.louis.compress_decompress.ICompress;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;

/**
 * @Date:09/07/2023 13:41
 * @Description:
 */
public class SevenZCompressor extends BaseCompressor implements ICompress {

    @Override
    protected void compressInternal(String sourceDir, String zipFilePath) throws IOException {
        File targetCompressFile = new File(sourceDir);
        try (SevenZOutputFile out = new SevenZOutputFile(new File(zipFilePath))) {
            if (targetCompressFile.isDirectory()) {
                Arrays.stream(targetCompressFile.listFiles()).forEach(f -> addToArchiveCompression(out, f, "."));
            } else {
                addToArchiveCompression(out, targetCompressFile, ".");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addToArchiveCompression(SevenZOutputFile out, File file, String dir) {
        String name = dir + File.separator + file.getName();
        if (dir.equals(".")) {
            name = file.getName();
        }
        if (file.isFile()) {
            SevenZArchiveEntry entry = null;
            FileInputStream in = null;
            try {
                entry = out.createArchiveEntry(file, name);
                out.putArchiveEntry(entry);
                in = new FileInputStream(file);
                byte[] b = new byte[1024];
                int count = 0;
                while ((count = in.read(b)) > 0) {
                    out.write(b, 0, count);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    out.closeArchiveEntry();
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } else if (file.isDirectory()) {
            File[] children = file.listFiles();
            if (children != null) {
                for (File child : children) {
                    addToArchiveCompression(out, child, name);
                }
            }
        } else {
            System.out.println(file.getName() + " is not supported");
        }
    }


    @Override
    protected void decompressInternal(String targetZipFilePath, String destDir) throws IOException {
        try {
            SevenZFile sevenZFile = new SevenZFile(new File(targetZipFilePath));
            SevenZArchiveEntry entry = sevenZFile.getNextEntry();
            while (entry != null) {
                File file = new File(destDir + File.separator + entry.getName());
                if (entry.isDirectory()) {
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    entry = sevenZFile.getNextEntry();
                    continue;
                }
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                FileOutputStream out = new FileOutputStream(file);
                byte[] content = new byte[(int) entry.getSize()];
                sevenZFile.read(content, 0, content.length);
                out.write(content);
                out.close();
                entry = sevenZFile.getNextEntry();
            }
            sevenZFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
