package com.tequeno.opencv;

import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

public class OpencvTest {

    static {
        System.load("E:/hexk/github/hey-jude/docs-added/opencv/opencv_java490.dll");
    }

    public static void main(String[] args) {
        String path = "/data/pic/2e98c4725bcc9e16958ca4ba93f6a019.jpeg";
        Mat mat = Imgcodecs.imread(path);
        if (mat.empty()) {
            System.out.println("图片加载失败！");
            return;
        }
        HighGui.imshow("Loaded Image", mat);
        HighGui.waitKey(0);

        mat.release();
    }
}
