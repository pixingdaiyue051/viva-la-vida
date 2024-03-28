package com.tequeno.io;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class ImageTest {

    private static final String DIR = "/data/1/";
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
        try (ByteArrayInputStream bis = new ByteArrayInputStream(decodedBytes); FileOutputStream fos = new FileOutputStream(outPath)) {

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
        String base64 = null;
        // 创建FileInputStream对象
        try (FileInputStream fis = new FileInputStream(file)) {
            // 获取文件字节
            byte[] bytes = new byte[(int) file.length()];
            fis.read(bytes);
            base64 = Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64;
    }

    /**
     * base64编码解码
     * 编码 输入字节数组 输出字符串
     * 解码 输入字符串 输出字节数组
     */
    private void base64Codec() {

        // 编码
        final Base64.Encoder encoder = Base64.getEncoder();
        final String encoded = encoder.encodeToString("hjkjkkjkdsfgdvdgsdgd".getBytes(StandardCharsets.UTF_8));
        System.out.println(encoded);

        // 解码
        final Base64.Decoder decoder = Base64.getDecoder();
        final byte[] decoded = decoder.decode("aGpramtramtkc2ZnZHZkZ3NkZ2Q=");
        System.out.println(new String(decoded, StandardCharsets.UTF_8));

    }

    public static void main(String[] args) throws IOException {
        ImageTest imageTest = new ImageTest();

        String base64 = imageTest.imageToBase64("/data/pic/2e98c4725bcc9e16958ca4ba93f6a019.jpg");
        System.out.println(base64);
        imageTest.base64ToImage(base64);
    }
}