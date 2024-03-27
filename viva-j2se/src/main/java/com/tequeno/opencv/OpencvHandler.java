package com.tequeno.opencv;

import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;

public class OpencvHandler {

//    static {
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//    }

    static {
        System.load("E:/hexk/github/hey-jude/docs-added/opencv/opencv_java490.dll");
    }

    public void openImg(String path) {
        Mat mat = Imgcodecs.imread(path);
        if (mat.empty()) {
            System.out.println("图片加载失败！");
            return;
        }
        HighGui.imshow("Loaded Image", mat);
        HighGui.waitKey();

        mat.release();

        System.exit(0);
    }

    public void detectFace(String path) {
        // 加载人脸检测器
        CascadeClassifier faceDetector = new CascadeClassifier("/hexk/github/hey-jude/docs-added/opencv/haarcascades/haarcascade_frontalface_default.xml");

        // 读取图像
        Mat src = Imgcodecs.imread(path);

        if (src.empty()) {
            System.out.println("Could not read the image");
            return;
        }

        // 进行人脸检测
        MatOfRect faces = new MatOfRect();
        faceDetector.detectMultiScale(src, faces, 1.1, 4, Objdetect.CASCADE_SCALE_IMAGE, new Size(30, 30), new Size());

        // 绘制矩形框
        for (Rect rect : faces.toArray()) {
            Imgproc.rectangle(src, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0), 3);
        }

        // 保存处理过的图像
//        String outPath = "/data/1/" + System.currentTimeMillis() + ".jpg";
//        Imgcodecs.imwrite(outPath, src);

        // 若要在窗口显示图像（可选）
         HighGui.imshow("Detected Faces", src);
         HighGui.waitKey();

        // 释放资源
        src.release();
        faces.release();
        System.exit(0);

    }

    public void compareFace(String path1,String path2) {
        // 加载人脸检测器
        CascadeClassifier classifier = new CascadeClassifier("/hexk/github/hey-jude/docs-added/opencv/haarcascades/haarcascade_frontalface_default.xml");

        // 读取图片
        Mat img1 = Imgcodecs.imread(path1);
        Mat img2 = Imgcodecs.imread(path2);

        // 检测两张图片中的脸
        MatOfRect faces1 = new MatOfRect();
        MatOfRect faces2 = new MatOfRect();

        classifier.detectMultiScale(img1, faces1);
        classifier.detectMultiScale(img2, faces2);

        if(faces1.empty() || faces2.empty()) {
            System.out.println("没有检测到人脸");
            return;
        }

        Rect rect1 = faces1.toList().get(0);
        Rect rect2 = faces2.toList().get(0);

        // 对比两个面部区域
        // 提取和对比面部区域
        Mat face1 = new Mat(img1, rect1);
        Mat face2 = new Mat(img2, rect2);

        // 可以使用不同的方法比如L1距离、L2距离、巴特沃斯距离等
        // 这里使用简单的L1距离作为例子
        double dist = Core.norm(face1, face2, Core.NORM_L1);

        // 两张脸的相似度可以通过一个规范化的值来表示
        // 这里假设最接近为0，最不接近为1
        double similarity = 1 - (dist / (Math.max(face1.cols(), face2.cols()) * face1.cols() * 255));
        System.out.println("Face similarity: " + similarity);
        System.exit(0);
    }
}
