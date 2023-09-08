package com.louis.compress_decompress.impl;

import com.louis.compress_decompress.BaseCompressor;
import com.louis.compress_decompress.ICompress;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @Date:09/07/2023 11:14
 * @Description:
 */
public class ZipCompressor extends BaseCompressor implements ICompress {

    @Override
    public void compressInternal(String sourceDir, String zipFilePath) throws IOException {
        FileOutputStream fos = new FileOutputStream(zipFilePath);
        ZipOutputStream zos = new ZipOutputStream(fos);

        File file = new File(sourceDir);
        compressFile(file, zos, "");

        zos.closeEntry();
        zos.close();
    }

    private static void compressFile(File file, ZipOutputStream zos, String parentDir) throws IOException {
        if (file.isDirectory()) {
            for (File nestedFile : file.listFiles()) {
                compressFile(nestedFile, zos, parentDir + file.getName() + "/");
            }
            return;
        }

        FileInputStream fis = new FileInputStream(file);

        zos.putNextEntry(new ZipEntry(parentDir + file.getName()));

        byte[] buffer = new byte[1024];
        int length;
        while ((length = fis.read(buffer)) > 0) {
            zos.write(buffer, 0, length);
        }

        fis.close();
    }

    @Override
    public void decompressInternal(String zipFilePath, String destDir) throws IOException {
        FileInputStream fis = new FileInputStream(zipFilePath);
        ZipInputStream zis = new ZipInputStream(fis);

        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null) {
            if (entry.getName().contains(".DS_Store")) {
                continue;
            }
            String filePath = destDir + File.separator + entry.getName();

            if (entry.isDirectory()) {
                File dir = new File(filePath);
                dir.mkdirs();
            } else {
                File parentFile = new File(filePath).getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }

                FileOutputStream fos = new FileOutputStream(filePath);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, length);
                }

                fos.close();
            }
            zis.closeEntry();
        }

        zis.close();
    }
}
