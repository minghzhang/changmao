package com.louis.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

/**
 * @date : 2021/9/23
 */
public class PdfUtils {

    //经过测试,dpi为96,100,105,120,150,200中,105显示效果较为清晰,体积稳定,dpi越高图片体积越大,一般电脑显示分辨率为96
    public static final float DEFAULT_DPI = 144;
    //默认转换的图片格式为jpg
    public static final String DEFAULT_FORMAT = "jpg";

    public final static Logger logger = LoggerFactory.getLogger(PdfUtils.class);

    private static final String PROPERTY_KEY = "sun.java2d.cmm";

    private static final String PROPERTY_VALUE = "sun.java2d.cmm.kcms.KcmsServiceProvider";


    /**
     * pdf转图片
     *
     * @param pdfPath PDF路径
     * @return 图片路径
     */
    public static void pdfToImage(String pdfPath, String imgPath) {
        try {
            logger.debug(">>处理开始");
            System.setProperty(PROPERTY_KEY, PROPERTY_VALUE);
            //图像合并使用参数
            // 总宽度
            int width = 0;
            // 保存一张图片中的RGB数据
            int[] singleImgRGB;
            int shiftHeight = 0;
            //保存每张图片的像素值
            BufferedImage imageResult = null;
            File pdfFile = new File(pdfPath);
            //利用PdfBox生成图像
            PDDocument pdDocument = PDDocument.load(pdfFile);
            PDFRenderer renderer = new PDFRenderer(pdDocument);
            //生成目录
            String fileRealName = pdfFile.getName().replace(".pdf", "");
            File fileDir = new File(imgPath + "/" + fileRealName);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            //循环每个页码
            for (int i = 0, len = pdDocument.getNumberOfPages(); i < len; i++) {
                BufferedImage image = renderer.renderImageWithDPI(i, DEFAULT_DPI, ImageType.RGB);
                int imageHeight = image.getHeight();
                int imageWidth = image.getWidth();
                //计算高度和偏移量
                //使用第一张图片宽度;
                width = imageWidth;
                //保存每页图片的像素值
                imageResult = new BufferedImage(width, imageHeight, BufferedImage.TYPE_INT_RGB);
                singleImgRGB = image.getRGB(0, 0, width, imageHeight, null, 0, width);
                // 写入流中
                imageResult.setRGB(0, shiftHeight, width, imageHeight, singleImgRGB, 0, width);
                // 写图片
                ImageIO.write(imageResult, DEFAULT_FORMAT, new File(imgPath + fileRealName + "/" + fileRealName + "_" + (i + 1) + ".jpg"));
                if (i % 10 == 0 || i + 1 == len) {
                    logger.debug("处理进度:{}/{}", i + 1, len);
                }
            }
            pdDocument.close();
            logger.debug("处理结束>>");
        } catch (Exception e) {
            logger.error("PDF转图片失败:{}", e);
            e.printStackTrace();
        }
    }


    public static void pdfToImage(File pdfFile, String imgPath) {
        try {
            logger.debug(">>处理开始");
            System.setProperty(PROPERTY_KEY, PROPERTY_VALUE);
            //图像合并使用参数
            // 总宽度
            int width = 0;
            // 保存一张图片中的RGB数据
            int[] singleImgRGB;
            int shiftHeight = 0;
            //保存每张图片的像素值
            BufferedImage imageResult = null;
            //利用PdfBox生成图像
            PDDocument pdDocument = PDDocument.load(pdfFile);
            PDFRenderer renderer = new PDFRenderer(pdDocument);
            //生成目录
            String fileRealName = pdfFile.getName().replace(".pdf", "");
            File fileDir = new File(imgPath + "/" + fileRealName);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            //循环每个页码
            for (int i = 0, len = pdDocument.getNumberOfPages(); i < len; i++) {
                BufferedImage image = renderer.renderImageWithDPI(i, DEFAULT_DPI, ImageType.RGB);
                int imageHeight = image.getHeight();
                int imageWidth = image.getWidth();
                //计算高度和偏移量
                //使用第一张图片宽度;
                width = imageWidth;
                //保存每页图片的像素值
                imageResult = new BufferedImage(width, imageHeight, BufferedImage.TYPE_INT_RGB);
                singleImgRGB = image.getRGB(0, 0, width, imageHeight, null, 0, width);
                // 写入流中
                imageResult.setRGB(0, shiftHeight, width, imageHeight, singleImgRGB, 0, width);
                // 写图片
                ImageIO.write(imageResult, DEFAULT_FORMAT, new File(imgPath + fileRealName + "/" + fileRealName + "_" + (i + 1) + ".jpg"));
                if (i % 10 == 0 || i + 1 == len) {
                    logger.debug("处理进度:{}/{}", i + 1, len);
                }
            }
            pdDocument.close();
            logger.debug("处理结束>>");
        } catch (Exception e) {
            logger.error("PDF转图片失败:{}", e);
            e.printStackTrace();
        }
    }

    public static void pdfToImage(InputStream pdfFileInputStream, String fileName, String imgPath) {
        try {
            logger.debug(">>处理开始");
            System.setProperty(PROPERTY_KEY, PROPERTY_VALUE);
            //图像合并使用参数
            // 总宽度
            int width = 0;
            // 保存一张图片中的RGB数据
            int[] singleImgRGB;
            int shiftHeight = 0;
            //保存每张图片的像素值
            BufferedImage imageResult = null;
            //利用PdfBox生成图像
            PDDocument pdDocument = PDDocument.load(pdfFileInputStream);
            PDFRenderer renderer = new PDFRenderer(pdDocument);
            //生成目录
            String fileRealName = fileName.replace(".pdf", "");
            File fileDir = new File(imgPath + "/" + fileRealName);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            //循环每个页码
            for (int i = 0, len = pdDocument.getNumberOfPages(); i < len; i++) {
                BufferedImage image = renderer.renderImageWithDPI(i, DEFAULT_DPI, ImageType.RGB);
                int imageHeight = image.getHeight();
                int imageWidth = image.getWidth();
                //计算高度和偏移量
                //使用第一张图片宽度;
                width = imageWidth;
                //保存每页图片的像素值
                imageResult = new BufferedImage(width, imageHeight, BufferedImage.TYPE_INT_RGB);
                singleImgRGB = image.getRGB(0, 0, width, imageHeight, null, 0, width);
                // 写入流中
                imageResult.setRGB(0, shiftHeight, width, imageHeight, singleImgRGB, 0, width);
                // 写图片
                ImageIO.write(imageResult, DEFAULT_FORMAT, new File(imgPath + fileRealName + "/" + fileRealName + "_" + (i + 1) + ".jpg"));
                if (i % 10 == 0 || i + 1 == len) {
                    logger.debug("处理进度:{}/{}", i + 1, len);
                }
            }
            pdDocument.close();
            logger.debug("处理结束>>");
        } catch (Exception e) {
            logger.error("PDF转图片失败:{}", e);
            e.printStackTrace();
        }
    }
}
