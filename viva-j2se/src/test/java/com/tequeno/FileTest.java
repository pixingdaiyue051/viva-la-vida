package com.tequeno;

import com.tequeno.file.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.StandardChartTheme;
import org.junit.Test;

import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileTest {

    @Test
    public void testBigfile() {
        BigfileWriter bigfileWriter = new BigfileWriter();

        String filePath = "/data/doc/bigfile.dat";
        File file = bigfileWriter.prepareFile(filePath);
        bigfileWriter.generateData(file);

//        BigfileReader reader = new BigfileReader();
//        reader.singleRead(file);
//        reader.threadRead(file);
//        reader.threadReadPlus(file);
    }

    @Test
    public void testFileHandler() {
//        File file = new File("D:\\hexk\\测试数据文档\\全员类.pdf");
//        String name = file.getName();
//        System.out.println(name);
//        long freeSpace = file.getFreeSpace();
//        System.out.println(freeSpace);
//        long length = file.length();
//        System.out.println(length);

//        // nio生产文件流
//        long uniqueWords = 0;
//        try (Stream<String> lines =
//                     Files.lines(Paths.get("doc/data.txt").normalize(), Charset.defaultCharset())) {
//            uniqueWords = lines.flatMap(line -> Arrays.stream(line.split("")))
////                    .distinct()
////                    .filter(l -> !l.matches(" "))
//                    .count();// 统计文件字符数
////            uniqueWords = lines.count();// 行数
//            System.out.println(uniqueWords);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        FileHandler fileHandler = new FileHandler();
//        fileHandler.recursiveListFile("/hexk/github/ht-c");
//        fileHandler.recursiveListFile("/hexk/github/ht-c", "/data/1/1.doc");
        fileHandler.randomAccessFile("/data/1/1.doc", "/data/1/2.doc");
    }

    @Test
    public void testImage() {
        ImageHandler imageHandler = new ImageHandler();

//        String base64 = imageHandler.imageToBase64("/data/pic/face/7b2e1bdd55631cee1acf2315c5f708cd.jpg");
//        System.out.println(base64);
//        imageHandler.base64ToImage(base64);

//        String base64 = imageHandler.urlToBase64("https://school-dev01.oss-cn-hangzhou.aliyuncs.com/student_face/545325014325530624.jpg?Expires=1718849524&OSSAccessKeyId=LTAI5tCy4nFXF3KFTx18daWb&Signature=SkpS%2FSXet7MXsDhV6rl%2BsKY%2B%2FX0%3D");
//        System.out.println(base64);

        imageHandler.compressImage("/data/pic/67e599683d27cb9886b202026e3063fb.jpeg", "/data/1/1.jpeg", 0.5f);
    }

    @Test
    public void testJFreeChart() throws Exception {
        //创建主题样式
        StandardChartTheme mChartTheme = new StandardChartTheme("CN");
        //设置标题字体
        mChartTheme.setExtraLargeFont(new Font("黑体", Font.BOLD, 20));
        //设置轴向字体
        mChartTheme.setLargeFont(new Font("宋体", Font.PLAIN, 15));
        //设置图例字体
        mChartTheme.setRegularFont(new Font("宋体", Font.PLAIN, 15));
        //应用主题样式
        ChartFactory.setChartTheme(mChartTheme);
        JFreeChartHandler test = new JFreeChartHandler();
        test.bar();
        test.pie();
    }

    @Test
    public void testPdf() {
        PdfHandler p = new PdfHandler();
        Map<String, Object> hashMap = new HashMap<>(11);
        hashMap.put("prisonName", "A监狱");
        hashMap.put("sunIndex", 3391.8D);
        hashMap.put("meetCount", 300);
        hashMap.put("totalCriCount", 278);
        hashMap.put("criCount", 71);
        try {
            p.run(hashMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testZip() {
        ZipHandler zipHandler = new ZipHandler();

        List<File> fileList = List.of(
                new File("/data/pic/1732678837581.jpg"),
                new File("/data/pic/h5_lqxpfzzxx.jpg"),
                new File("/data/pic/icon-moon.png")
        );
        zipHandler.toZip(fileList, "/data/doc/test.zip");

        zipHandler.unzip("/data/doc/test.zip", "/data/doc");
    }

    @Test
    public void testQrcode() {
        QrcodeHandler handler = new QrcodeHandler();
        handler.generateQrcode("https://www.baidu.com", "cs");
        String decoded = handler.decodeQrcode("/data/pic/cs.jpg");
        System.out.println(decoded);

    }

    @Test
    public void testNio() {
        NioHandler handler = new NioHandler();
//        handler.buffer();
//        handler.fileOutChannel("/data/doc/1.txt");
//        handler.fileInChannel("/data/doc/1.txt");
        handler.fileTransfer("/data/doc/1.txt", "/data/doc/2.txt");
    }

    @Test
    public void testSerialize() {
        SerializeHandler handler = new SerializeHandler();
//        handler.load();

        handler.read();
    }
}
