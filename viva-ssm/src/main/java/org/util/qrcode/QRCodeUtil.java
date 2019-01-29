package org.util.qrcode;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRCodeUtil {

	private static final String CHARSET = "UTF-8";
	private static final String FORMAT_NAME = "jpg";
	private static final boolean NEED_COMPRESS = true;
	// 二维码尺寸
	private static final int QRCODE_SIZE = 300;
	// LOGO宽度
	private static final int LOGO_SIZE = 100;

	private static BufferedImage createImage(String content, String imgPath, boolean needCompress) throws Exception {
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		/*
		 * 二维码的纠错级别(排错率),4个级别： L (7%)、 M (15%)、 Q (25%)、 H (30%)(最高H)
		 * 纠错信息同样存储在二维码中，纠错级别越高，纠错信息占用的空间越多，那么能存储的有用讯息就越少；共有四级； 选择M，扫描速度快。
		 */
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
		// 二维码边界空白大小 1,2,3,4 (4为默认,最大)
		hints.put(EncodeHintType.MARGIN, 1);
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE,
				hints);
		int width = bitMatrix.getWidth();
		int height = bitMatrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
			}
		}
		if (StringUtils.isBlank(imgPath)) {
			return image;
		}
		File f = new File(imgPath);
		if (!f.exists() || f.isDirectory()) {
			throw new Exception("无法加载对应路径" + imgPath + "下的图片");
		}
		// 插入图片
		insertImage(image, imgPath, needCompress);
		return image;
	}

	private static void insertImage(BufferedImage source, String imgPath, boolean needCompress) throws Exception {
		Image src = ImageIO.read(new File(imgPath));
		int width = src.getWidth(null);
		int height = src.getHeight(null);
		if (needCompress) { // 压缩LOGO
			if (width > LOGO_SIZE) {
				width = LOGO_SIZE;
			}
			if (height > LOGO_SIZE) {
				height = LOGO_SIZE;
			}
			Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(image, 0, 0, null); // 绘制缩小后的图
			g.dispose();
			src = image;
		}
		// 插入LOGO
		Graphics2D graph = source.createGraphics();
		int x = (QRCODE_SIZE - width) / 2;
		int y = (QRCODE_SIZE - height) / 2;
		graph.drawImage(src, x, y, width, height, null);
		Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
		graph.setStroke(new BasicStroke(3f));
		graph.draw(shape);
		graph.dispose();
	}

	private static void mkdirs(String destPath) {
		File file = new File(destPath);
		// 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
		}
	}

	public static Map<String, Object> writ2File(String content, String imgPath, String destPath, String codeName) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isBlank(codeName)) {
				codeName = new Date().getTime() + "." + FORMAT_NAME;
			}
			BufferedImage image = createImage(content, imgPath, NEED_COMPRESS);
			destPath = destPath.replaceAll("\\\\", "/") + "/" + codeName;
			mkdirs(destPath);
			ImageIO.write(image, FORMAT_NAME, new File(destPath));
			map.put("code", "1");
			map.put("msg", "图片已生成");
		} catch (Exception e) {
			map.clear();
			map.put("code", "0");
			map.put("msg", e.getMessage());
			e.printStackTrace();
		}
		return map;
	}

	public static void writ2Stream(String content, String imgPath, OutputStream out) {
		try {
			BufferedImage image = createImage(content, imgPath, NEED_COMPRESS);
			ImageIO.write(image, FORMAT_NAME, out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Map<String, Object> decode(String path) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", "0");
		try {
			File f = new File(path);
			if (!f.exists() || f.isDirectory()) {
				map.put("msg", "无法加载对应路径" + path + "下的图片");
				return map;
			}
			BufferedImage image = ImageIO.read(f);
			if (image == null) {
				map.put("msg", "读取图片失败");
				return map;
			}
			BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
			Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
			hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
			Result result = new MultiFormatReader().decode(bitmap, hints);
			if (result == null) {
				map.put("msg", "读取图片失败");
				return map;
			} else {
				map.put("msg", result.getText());
				map.put("code", 1);
				return map;
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.clear();
			map.put("code", 0);
			map.put("msg", e.getMessage());
			return map;
		}
	}

	public static void main(String[] args) {
		Map<String, Object> map = QRCodeUtil.writ2File("效果嘎嘎嘎", "", "E:/Files/hexk/imgs/QRCode/", "");
		String code = map.get("code").toString();
		String msg = map.get("msg").toString();
		System.out.println(code);
		System.out.println(msg);
	}

}
