## 使用tess4j java  api 调用 tesseract

### 引用test4j api

```xml

<dependency>
    <groupId>net.sourceforge.tess4j</groupId>
    <artifactId>tess4j</artifactId>
    <version>4.3.1</version>
</dependency>
```

### demo

```java
public class Test4jOCRAPI {

    public static Logger logger = LoggerFactory.getLogger(Test4jOCRAPI.class);

    /**
     * TODO 图片识别-通过图片并指定语言，获取识别结果
     *
     * @param srImage 图片路径
     * @param ZH_CN   是否使用中文训练字库,true-是
     * @return 识别结果
     */
    public static String FindOCR(String srImage, boolean ZH_CN) {
        try {
            double start = System.currentTimeMillis();
            File imageFile = new File(srImage);
            if (!imageFile.exists()) {
                return "图片不存在";
            }
            BufferedImage textImage = ImageIO.read(imageFile);
            Tesseract instance = new Tesseract();
            //windows 需要配置环境变量有TESSDATA_PREFIX = C:\Program Files\Tesseract-Tess4jOCRAPI\tessdata 注意：配置完成后需要重启系统
            //Linux同理
            //System.setProperty("TESSDATA_PREFIX","/usr/local/Cellar/tesseract/4.1.1/share/tessdata/");
            System.setProperty("TESSDATA_PREFIX", "/usr/local/Cellar/tesseract/4.1.1");
            instance.setDatapath(System.getenv("TESSDATA_PREFIX"));       //设置训练字库的位置
            if (ZH_CN)
                instance.setLanguage("chi_sim");//中文识别
            else
                instance.setLanguage("eng");    //英文识别
            String result = null;
            result = instance.doOCR(textImage);
            double end = System.currentTimeMillis();
            logger.info("本次识别耗时：{} ms ", (end - start));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "发生未知错误";
        }
    }


    public static void main(String[] args) throws Exception {
        //1.1 只需要安装完并做好基础配置即可 - 第一篇即可
        String path = "/Users/landon.zhang/Documents/SourceCode/image/ch9.jpg";
        File remoteFile = new File(path);
        String result = FindOCR(remoteFile.toString(), true);
        System.out.println(result);
    }

}
```

### 错误解决方法

报这个错 mac Unable to load library 'tesseract': Native library (darwin/libtesseract.dylib)
可以使用下面的方法解决该错误

```shell
cd /Users/username/.m2/repository/net/sourceforge/tess4j/tess4j/4.5.4/
mkdir darwin
jar uf tess4j-4.5.4.jar darwin/
brew info tesseract (here you can find path to libtesseract.4.dylib)
cp /usr/local/Cellar/tesseract/4.1.1/lib/libtesseract.4.dylib darwin/libtesseract.dylib
jar uf tess4j-4.5.4.jar darwin/libtesseract.dylib
jar tf tess4j-4.5.4.jar
```