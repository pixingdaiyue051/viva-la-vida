package com.tequeno.file;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
        URLConnection connection = null;
        try {
            URL url = new URL(urlPath);
            connection = url.openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
            throw new RuntimeException(e);
        }
    }


    private static final int WIDTH = 250;
    private static final int HEIGHT = 300;
    private static final int FONT_SIZE = 18;


    /**
     * 由字符串生成二维码BufferedImage对象
     *
     * @param content 字符串内容
     * @return
     * @throws Exception
     */
    private BufferedImage generate(String content) throws Exception {

        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8);// 指定字符编码为UTF-8
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);// 指定二维码的纠错等级为中级
        hints.put(EncodeHintType.MARGIN, 1);// 设置图片的边距

        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);

        // 写到字节数据中
        // MatrixToImageWriter.writeToStream(bitMatrix, SUFFIX, os);
        // resultImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        // ImageIO.write(resultImage, SUFFIX, os);

        // 写到文件中
        // MatrixToImageWriter.writeToPath(bitMatrix, SUFFIX, Paths.get(DEFAULT_PATH));

        // 原始效果
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                resultImage.setRGB(x, y, bitMatrix.get(x, y) ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
            }
        }

        return resultImage;

////        // 去白边
//        int[] rectangle = bitMatrix.getEnclosingRectangle();
//        int recWidth = rectangle[2] + 1;
//        int recHeight = rectangle[3] + 1;
//        BitMatrix newBitMatrix = new BitMatrix(recWidth, recHeight);
//        newBitMatrix.clear();
//        for (int i = 0; i < recWidth; i++) {
//            for (int j = 0; j < recHeight; j++) {
//                if (bitMatrix.get(i + rectangle[0], j + rectangle[1])) {
//                    newBitMatrix.set(i, j);
//                }
//            }
//        }
//
//        int width = newBitMatrix.getWidth();
//        int height = newBitMatrix.getHeight();
//        BufferedImage newResultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//        for (int x = 0; x < width; x++) {
//            for (int y = 0; y < height; y++) {
//                newResultImage.setRGB(x, y, newBitMatrix.get(x, y) ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
//            }
//        }
//
//        return newResultImage;
    }

    /**
     * 添加中间logo
     *
     * @param source   二维码图片
     * @param logoFile logo
     */
    private void insertLogo(BufferedImage source, File logoFile) throws IOException {
        Graphics2D graph = source.createGraphics();

        // 读取Logo
        BufferedImage logo = ImageIO.read(logoFile);
        // 获得Logo位置
        int startX = source.getWidth() / 5 * 2;
        int startY = source.getHeight() / 5 * 2;
        int width = source.getWidth() / 5;
        int height = source.getHeight() / 5;

        graph.drawImage(logo, startX, startY, width, height, null);
//        graph.setStroke(new BasicStroke(1.2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
//        graph.setColor(Color.RED);
//        graph.drawRoundRect(startX, startY, width, height, 15, 15);

        // 清除画笔工具
        graph.dispose();
//        // 刷新二维码
//        source.flush();
    }

    /**
     * 插入底部文本
     *
     * @param source 二维码图片
     * @param text   二维码底部文本内容
     */
    private void insertText(BufferedImage source, String text) {
        int width = source.getWidth();
        int height = source.getHeight();
        Graphics2D graph = source.createGraphics();
        // 计算文字开始的位置(居中显示)
        // x开始的位置：（图片宽度-字体大小*字的个数）/2
        int startX = (width - (FONT_SIZE * text.length())) / 2;
        // y开始的位置：图片高度-（图片高度-图片宽度）/2
        int startY = height - (height - width) / 2;
        graph.setColor(Color.BLACK);
        Font font = new Font(null, Font.BOLD, FONT_SIZE);
        graph.setFont(font);
        graph.drawString(text, startX, startY);
        // 清除画笔工具
        graph.dispose();
    }

    /**
     * 生成二维码
     *
     * @param content    字符串内容
     * @param qrcodeName 二维码文件名
     */
    public void generateQrcode(String content, String qrcodeName) {
        try (FileOutputStream output = new FileOutputStream(DIR + qrcodeName + "." + SUFFIX)) {
            BufferedImage image = this.generate(content);
            ImageIO.write(image, SUFFIX, output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成中间有logo的二维码
     *
     * @param content    字符串内容
     * @param qrcodeName 二维码文件名
     * @param logoFile   logo
     */
    public void generateQrcode(String content, String qrcodeName, File logoFile) {
        try (FileOutputStream output = new FileOutputStream(DIR + qrcodeName + "." + SUFFIX)) {
            BufferedImage image = this.generate(content);
            insertLogo(image, logoFile);
            ImageIO.write(image, SUFFIX, output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成底部有文字的二维码
     *
     * @param content    字符串内容
     * @param qrcodeName 二维码文件名
     * @param text       底部文字
     */
    public void generateQrcode(String content, String qrcodeName, String text) {
        try (FileOutputStream output = new FileOutputStream(DIR + qrcodeName + "." + SUFFIX)) {
            BufferedImage image = this.generate(content);
            insertText(image, text);
            ImageIO.write(image, SUFFIX, output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成有logo和底部文字的二维码
     *
     * @param content    字符串内容
     * @param qrcodeName 二维码文件名
     * @param logoFile   logo
     * @param text       底部文字
     */
    public void generateQrcode(String content, String qrcodeName, File logoFile, String text) {
        try (FileOutputStream output = new FileOutputStream(DIR + qrcodeName + "." + SUFFIX)) {
            BufferedImage image = this.generate(content);
            insertLogo(image, logoFile);
            insertText(image, text);
            ImageIO.write(image, SUFFIX, output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析二维码
     *
     * @param path
     * @return
     */
    public String decodeQrcode(String path) {
        try {
            File file = new File(path);
            BufferedImage image = ImageIO.read(file);
            BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            HashMap<DecodeHintType, Object> hints = new HashMap<>();
            hints.put(DecodeHintType.CHARACTER_SET, StandardCharsets.UTF_8);
            Result result = new QRCodeReader().decode(bitmap, hints);
            return result.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}