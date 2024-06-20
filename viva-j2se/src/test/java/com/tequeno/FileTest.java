package com.tequeno;

import com.tequeno.file.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.StandardChartTheme;
import org.junit.Test;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileTest {

    @Test
    public void testBigfile() {
        BigfileWriter bigfileWriter = new BigfileWriter();

        String filePath = "/data/1/bigfile.dat";
        File file = bigfileWriter.prepareFile(filePath);
//        bigfileWriter.generateData(file);

        BigfileReader reader = new BigfileReader();
//        reader.singleRead(file);
//        reader.threadRead(file);
        reader.threadReadPlus(file);
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
        FileHandler fileTest = new FileHandler();
        fileTest.recursiveListFile();
//        fileTest.recursiveListFile(Paths.get("../viva-la-vida").normalize().toString());
//        fileTest.readFile();
//        fileTest.base64();
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
        List<File> fileList = new ArrayList<>();
        fileList.add(new File("/data/pic/5639395138950405.jpg"));
        fileList.add(new File("/data/pic/6668538023345750.jpg"));
        fileList.add(new File("/data/pic/16575137789103803.jpg"));
        new ZipHandler().toZip(fileList, "/data/pic/pink_floyd2.zip");
    }

    @Test
    public void testQrcode() {
        QrcodeHandler handler = new QrcodeHandler();
//        handler.generateQrcode("https://www.baidu.com", "测试二维码");

//      1750371575276056577,濮阳市西湖中学
//      1790644721651707905,南乐近德固中学
//      1790922566457786369,濮阳市第一高级中学

//        handler.generateQrcode("https://jiansuotong.top:9002/h5?schoolId=1750371575276056577&schoolname=濮阳市西湖中学", "h5_xhzx", "濮阳市西湖中学");
//        handler.generateQrcode("https://jiansuotong.top:9002/h5?schoolId=1790644721651707905&schoolname=南乐近德固中学", "h5_nyjdgzx", "南乐近德固中学");
//        handler.generateQrcode("https://jiansuotong.top:9002/h5?schoolId=1790922566457786369&schoolname=濮阳市第一高级中学", "h5_dygjzx", "濮阳市第一高级中学");

//        File logo = new File("/data/pic/logo.jpg");
//        handler.generateQrcode("http://jiansuotong.top:8888/opt/xyks_app.apk", "xyks_app1", logo);

//        String out = handler.decodeQrcode("/data/pic/xyks_app.jpg");
//        System.out.println(out);

    }
}
