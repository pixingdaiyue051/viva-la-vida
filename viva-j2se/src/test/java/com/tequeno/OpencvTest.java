package com.tequeno;

import com.tequeno.opencv.OpencvHandler;
import org.junit.Test;

public class OpencvTest {

    @Test
    public void testDetect() {
        String path = "/data/pic/1c0245766496e2cf09cb0fd7b6bb5889.jpeg";
        OpencvHandler test = new OpencvHandler();
//        test.openImg(path);
        test.detectFace(path);
    }

    @Test
    public void testCompare() {
        String path1 = "/data/pic/face/1712471243101.jpg";
        String path2 = "/data/pic/face/1712471243100.jpg";
        OpencvHandler test = new OpencvHandler();
        test.compareFace(path1, path2);
    }
}
