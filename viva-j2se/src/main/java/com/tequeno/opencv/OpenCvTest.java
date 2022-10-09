package com.tequeno.opencv;

import org.opencv.core.Core;

public class OpenCvTest {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        MatHandler matHandler = new MatHandler();
//        matHandler.pic2mat();
//        matHandler.mat2pic();
//        matHandler.add();
//        matHandler.rotate();
//        matHandler.affine();
//        matHandler.perspective();
//        matHandler.threshold();
//        matHandler.cvt();
//        matHandler.morphology();
//        matHandler.pyr();
//        matHandler.calc();
//        matHandler.edge();
//        matHandler.template();
//        matHandler.hough();
//        matHandler.cornor();
//        matHandler.sift();
//        matHandler.cascade();
        matHandler.video();

    }
}