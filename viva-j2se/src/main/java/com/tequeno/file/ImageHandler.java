package com.tequeno.file;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Iterator;

public class ImageHandler {

    private static final String DIR = "/data/pic/";
    private static final String SUFFIX = "jpg";
    private static final String BASE64_PREFIX = "data:image/(png|jpg|jpeg|gif|bmp|ico);base64,";

    /**
     * base64字符串转换成图片
     * <p>
     * 这段代码首先移除了Base64字符串可能存在的数据类型前缀（例如"data:image/jpeg;base64,"），然后使用Base64.getDecoder().decode()方法将Base64编码的字符串解码成字节数组。
     * 接着通过ByteArrayInputStream和ImageIO.read()将字节数组还原为BufferedImage对象。最后，它将图像写入到指定路径的文件中。
     * <p>
     * 注意：上述代码适用于Java 8及以上版本，其中内置了Base64解码器。对于早期版本的Java，你可能需要引入Apache Commons Codec或其他库来处理Base64解码。
     * 如果Java版本低于8，需要使用Apache Commons Codec库进行Base64解码：
     * import org.apache.commons.codec.binary.Base64;
     * byte[] decodedBytes = Base64.decodeBase64(base64String);
     *
     * @param base64String
     */
    public void base64ToImage(String base64String) {
        String outPath = DIR + System.currentTimeMillis() + "." + SUFFIX;
//        File f = new File(outPath);
//        if (!f.getParentFile().exists()) {
//            f.getParentFile().mkdirs();
//        }
        // 去除Base64前缀
        base64String = base64String.replaceFirst(BASE64_PREFIX, "");
        // 将Base64字符串解码为字节数组
        byte[] decodedBytes = Base64.getDecoder().decode(base64String);
        try (ByteArrayInputStream bis = new ByteArrayInputStream(decodedBytes);
             FileOutputStream fos = new FileOutputStream(outPath)) {

            // 读取输入流并转换为BufferedImage对象
            BufferedImage image = ImageIO.read(bis);
            // 将BufferedImage写入到指定的图像文件
            ImageIO.write(image, SUFFIX, fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String imageToBase64(String imagePath) {
        // 创建一个File对象
        File file = new File(imagePath);
        // 创建FileInputStream对象
        try (FileInputStream fis = new FileInputStream(file);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            // 获取文件字节
            byte[] bytes = new byte[1024];
            int len;
            while ((len = fis.read(bytes)) > 0) {
                bos.write(bytes, 0, len);
            }
            return Base64.getEncoder().encodeToString(bos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String urlToBase64(String urlPath) {
        try {
            URL url = new URL(urlPath);
            URLConnection connection = url.openConnection();

            try (InputStream is = connection.getInputStream();
//             FileOutputStream fos = new FileOutputStream(DIR + System.currentTimeMillis() + "." + SUFFIX);
                 ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                byte[] bytes = new byte[1024];
                int len;
                while ((len = is.read(bytes)) > 0) {
//                fos.write(bytes, 0, len);
                    bos.write(bytes, 0, len);
                }
                return Base64.getEncoder().encodeToString(bos.toByteArray());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * base64编码解码
     * 编码 输入字节数组 输出字符串
     * 解码 输入字符串 输出字节数组
     */
    public void base64Codec() {

        // 编码
        final Base64.Encoder encoder = Base64.getEncoder();
        final String encoded = encoder.encodeToString("hjkjkkjkdsfgdvdgsdgd".getBytes(StandardCharsets.UTF_8));
        System.out.println(encoded);

        // 解码
        final Base64.Decoder decoder = Base64.getDecoder();
        final byte[] decoded = decoder.decode("aGpramtramtkc2ZnZHZkZ3NkZ2Q=");
        System.out.println(new String(decoded, StandardCharsets.UTF_8));

    }

    /**
     * 图片压缩
     *
     * @param inputPath
     * @param outputPath
     * @param quality
     */
    public void compressImage(String inputPath, String outputPath, float quality) {
        try {
            File inputFile = new File(inputPath);
            File outputFile = new File(outputPath);
            // 读取图片
            BufferedImage image = ImageIO.read(inputFile);

            // 获取图片写入工具
            Iterator<ImageWriter> writers = ImageIO.getImageWritersBySuffix(SUFFIX);
            ImageWriter writer = writers.next();

            // 设置压缩参数
            ImageWriteParam param = writer.getDefaultWriteParam();
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(quality); // 设置压缩质量

            // 将图片输出到文件
            FileImageOutputStream output = new FileImageOutputStream(outputFile);
            writer.setOutput(output);
            IIOImage iioImage = new IIOImage(image, null, null);
            writer.write(null, iioImage, param);

            // 关闭流
            output.close();
            writer.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}