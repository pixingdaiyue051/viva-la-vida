package com.tequeno.file;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class QrcodeHandler {

    private static final String DIR = "/data/pic/";
    private static final String SUFFIX = "jpg";

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
        try (FileOutputStream output = new FileOutputStream(DIR + qrcodeName + "." + SUFFIX);
//             ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ) {
            BufferedImage image = this.generate(content);
            insertText(image, text);
            ImageIO.write(image, SUFFIX, output);
//            ImageIO.write(image, SUFFIX, bos);
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
        return "";
    }
}
