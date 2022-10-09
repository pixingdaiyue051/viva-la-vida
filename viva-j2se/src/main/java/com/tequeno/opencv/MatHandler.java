package com.tequeno.opencv;


import org.opencv.core.*;
import org.opencv.features2d.Features2d;
import org.opencv.features2d.ORB;
import org.opencv.features2d.SIFT;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

import java.util.ArrayList;
import java.util.List;

public class MatHandler {

    /**
     * 读取托图片转换为矩阵
     */
    public void pic2mat() {
        String picPath = "doc/pic/cvlena.jpg";

        Mat src = Imgcodecs.imread(picPath);
        System.out.println(src.width());
        System.out.println(src.height());
        System.out.println(src.size());
//        System.out.println(src.dump());

//        HighGui.imshow("1", src);
//        HighGui.waitKey();

        System.out.println("end");
    }

    /**
     * 将矩阵点写入图片
     */
    public void mat2pic() {
        Mat ones = Mat.ones(4, 4, CvType.CV_8UC1);
        Imgcodecs.imwrite("doc/pic/cv_1121.jpg", ones);
        Mat eye = Mat.eye(10, 10, CvType.CV_8UC4);
        Imgcodecs.imwrite("doc/pic/cv_1122.jpg", eye);
    }

    public void cvt() {
        Mat src = Imgcodecs.imread("doc/pic/cvlena.jpg");
        Mat dst = new Mat();
        Imgproc.cvtColor(src, dst, Imgproc.COLOR_RGB2GRAY);
        Imgcodecs.imwrite("doc/pic/cvlena_cvt.jpg", dst);

        Mat mat = Imgcodecs.imread("doc/pic/cvlena.jpg", Imgcodecs.IMREAD_GRAYSCALE);
        Imgcodecs.imwrite("doc/pic/cvlena_cvt1.jpg", mat);
    }

    /**
     * 像素点累加  超出255使用255
     */
    public void add() {
        Mat mat1 = Imgcodecs.imread("doc/pic/cvrl.jpg");
        Mat mat2 = Imgcodecs.imread("doc/pic/cvbl.jpg");
        Mat dst = new Mat();
        Core.add(mat1, mat2, dst);
        Imgcodecs.imwrite("doc/pic/cv_rblcom.jpg", dst);

        // 加权累加
        Core.addWeighted(mat1, 0.5d, mat2, 0.5d, 1.0d, dst);
        Imgcodecs.imwrite("doc/pic/cv_rblcom_weight.jpg", dst);
    }

    /**
     * 旋转
     * 1. 选取一个旋转中心点
     * 2. 旋转原图片
     * 3. 应用旋转效果
     * 反相
     * 1. 像素点全部取反
     * 平移
     * 缩放
     * 1. 使用指定比例宽高重新绘制原图
     */
    public void rotate() {
        Mat src = Imgcodecs.imread("doc/pic/cvlena.jpg");
        Mat dst = new Mat();
//        // 旋转 选择中点
//        Point point = new Point(src.width() * 0.5d, src.height() * 0.5d);
//        // 设置旋转角度和缩放比例
//        Mat affine = Imgproc.getRotationMatrix2D(point, 33.0d, 1.0d);
//        Imgproc.warpAffine(src, dst, affine, src.size());
//        Imgcodecs.imwrite("doc/pic/cvlena_rotated.jpg", dst);
//
//        // 反相
//        Core.bitwise_not(src, dst);
//        Imgcodecs.imwrite("doc/pic/cvlena_bitnot.jpg", dst);

        // 平移 TODO
//        Imgproc.resize(src,dst, new Size(300.0d, 300.0d));
//        Imgcodecs.imwrite("doc/pic/cvlena_resized.jpg", dst);

        // 缩放
        Imgproc.resize(src, dst, new Size(300.0d, 300.0d));
        Imgcodecs.imwrite("doc/pic/cvlena_resized.jpg", dst);
    }

    /**
     * 仿射变换
     * 1. 确定图片上不在一条线的三个点
     * 2. 确定图片上不在一条线的另外三个点(可以和第一步的一样，不过没意义)
     * 3. 类型转换
     * 4. 应用效果
     */
    public void affine() {
        Mat src = Imgcodecs.imread("doc/pic/cvlena.jpg");
        Mat dst = new Mat();
        // 原始变换位置
        Point[] pt1 = new Point[3];
        pt1[0] = new Point(0.0d, 0.0d);
        pt1[1] = new Point(src.width(), 0.0d);
        pt1[2] = new Point(0.0d, src.height());
        // 目标变换点
        Point[] pt2 = new Point[3];
        pt2[0] = new Point(0.0d, 0.0d);
        pt2[1] = new Point(src.width(), 0.0d);
        pt2[2] = new Point(src.width() * 0.5d, src.height());
        // 类型转换
        MatOfPoint2f mop1 = new MatOfPoint2f(pt1);
        MatOfPoint2f mop2 = new MatOfPoint2f(pt2);
        // 应用转换效果到dst
        Mat transform = Imgproc.getAffineTransform(mop1, mop2);
        Imgproc.warpAffine(src, dst, transform, src.size());
        Imgcodecs.imwrite("doc/pic/cvlena_affine.jpg", dst);
    }

    /**
     * 透视变换 TODO
     * 1. 使用四个互相不在一条线上的点
     */
    public void perspective() {
        Mat src = Imgcodecs.imread("doc/pic/cvlena.jpg");
        Mat dst = new Mat();
        // 原始变换位置
        Point[] pt1 = new Point[4];
        pt1[0] = new Point(600.0d, 350.0d);
        pt1[1] = new Point(889.0d, 343.0d);
        pt1[2] = new Point(501.0d, 644.0d);
        pt1[3] = new Point(969.0d, 636.0d);
        // 目标变换点
        Point[] pt2 = new Point[4];
        pt2[0] = new Point(0.0d, 0.0d);
        pt2[1] = new Point(300.0d, 0.0d);
        pt2[2] = new Point(0.0d, 300.0d);
        pt2[3] = new Point(300.0d, 300.0d);
        // 类型转换
        MatOfPoint2f mop1 = new MatOfPoint2f(pt1);
        MatOfPoint2f mop2 = new MatOfPoint2f(pt2);
        // 应用转换效果到dst
        Mat transform = Imgproc.getPerspectiveTransform(mop1, mop2);
        Imgproc.warpPerspective(src, dst, transform, new Size(pt1[3]));
        Imgcodecs.imwrite("doc/pic/cvlena_perspective.jpg", dst);
    }

    /**
     * 阈值处理
     * 二值化
     * Imgproc.THRESH_BINARY 超出 threshold 设置为 maxval 其余 0
     * Imgproc.THRESH_BINARY_INV 反转 THRESH_BINARY
     * Imgproc.THRESH_TRUNC 超出 threshold 设置为 threshold 其余不变
     * Imgproc.THRESH_TOZERO 超出 threshold 不变 其余 0
     * Imgproc.THRESH_TOZERO_INV 反转 THRESH_TOZERO
     */
    public void threshold() {
        Mat src = Imgcodecs.imread("doc/pic/cvlena.jpg");
        Mat dst = new Mat();

        double threshold = 155.0d;
        double maxval = 200.0d;

        Imgproc.threshold(src, dst, threshold, maxval, Imgproc.THRESH_BINARY);
        Imgcodecs.imwrite("doc/pic/cvlena_binary.jpg", dst);
        Imgproc.threshold(src, dst, threshold, maxval, Imgproc.THRESH_BINARY_INV);
        Imgcodecs.imwrite("doc/pic/cvlena_binary_inv.jpg", dst);
        Imgproc.threshold(src, dst, threshold, maxval, Imgproc.THRESH_TRUNC);
        Imgcodecs.imwrite("doc/pic/cvlena_trunc.jpg", dst);
        Imgproc.threshold(src, dst, threshold, maxval, Imgproc.THRESH_TOZERO);
        Imgcodecs.imwrite("doc/pic/cvlena_tozero.jpg", dst);
        Imgproc.threshold(src, dst, threshold, maxval, Imgproc.THRESH_TOZERO_INV);
        Imgcodecs.imwrite("doc/pic/cvlena_tozero_inv.jpg", dst);
    }

    /**
     * 形态学操作
     * erode 腐蚀 将亮度区域减小
     * dilate 膨胀 将亮度区域增大
     * <p>
     * Imgproc.MORPH_OPEN 开运算 先腐蚀后膨胀
     * Imgproc.MORPH_CLOSE 闭运算 先膨胀后腐蚀
     * Imgproc.MORPH_GRADIENT 形态学梯度 腐蚀和膨胀的差  看起来像图像轮廓
     * Imgproc.MORPH_TOPHAT 开运算后与原图像的差
     * Imgproc.MORPH_BLACKHAT 闭运算后与原图像的差
     */
    public void morphology() {
        Mat src = Imgcodecs.imread("doc/pic/cvtest.jpg");
        Mat dst = new Mat();
        Mat kernel = new Mat(); // 操作核 默认是中心点 3*3矩阵
        Point anchor = new Point(1, 1); // 锚点 默认是kernel中心点
        int iter = 1; // 迭代次数 默认1

        // 均值模糊
        Imgproc.blur(src, dst, new Size(5, 5));
        Imgcodecs.imwrite("doc/pic/cvtest_blur.jpg", dst);

        // 高斯模糊
        Imgproc.GaussianBlur(src, dst, new Size(5, 5), 10, 10);
        Imgcodecs.imwrite("doc/pic/cvtest_blur_gauss.jpg", dst);

        // 中值模糊  降噪效果明显
        Imgproc.medianBlur(src, dst, 5);
        Imgcodecs.imwrite("doc/pic/cvtest_blur_median.jpg", dst);

        // 腐蚀
        Imgproc.erode(src, dst, kernel);
        Imgcodecs.imwrite("doc/pic/cvtest_erode.jpg", dst);

        // 膨胀
        Imgproc.dilate(src, dst, kernel);
        Imgcodecs.imwrite("doc/pic/cvtest_dilate.jpg", dst);

        // 形态学操作
        Imgproc.morphologyEx(src, dst, Imgproc.MORPH_OPEN, kernel);
        Imgcodecs.imwrite("doc/pic/cvtest_morph_open.jpg", dst);

        Imgproc.morphologyEx(src, dst, Imgproc.MORPH_CLOSE, kernel);
        Imgcodecs.imwrite("doc/pic/cvtest_morph_close.jpg", dst);

        Imgproc.morphologyEx(src, dst, Imgproc.MORPH_GRADIENT, kernel);
        Imgcodecs.imwrite("doc/pic/cvtest_morph_gradient.jpg", dst);

        Imgproc.morphologyEx(src, dst, Imgproc.MORPH_TOPHAT, kernel);
        Imgcodecs.imwrite("doc/pic/cvtest_morph_tophat.jpg", dst);

        Imgproc.morphologyEx(src, dst, Imgproc.MORPH_BLACKHAT, kernel);
        Imgcodecs.imwrite("doc/pic/cvtest_morph_blackhat.jpg", dst);

    }

    /**
     * 图像金字塔
     * 1. 图形分层
     * 2. 越处在底层的图层 分辨率越高
     * 向下采样 从底部向顶部采样 分辨率越来越低
     * 向上采样 从顶部向底部采样 分辨率越来越高
     * <p>
     * 高斯金字塔 TODO
     * 拉普拉斯金字塔
     * 1. 对原始图像向下采样
     * 2. 再向上采样
     * 3. 计算与原图像的差
     */
    public void pyr() {
        Mat src = Imgcodecs.imread("doc/pic/cvboy.jpg");
        Mat dst = new Mat();
//        HighGui.imshow("0", src);
//        HighGui.waitKey();

        Imgproc.pyrDown(src, dst);
//        HighGui.imshow("1", dst);
//        HighGui.waitKey();

        Imgproc.pyrUp(dst, dst);
//        HighGui.imshow("2", dst);
//        HighGui.waitKey();

        Mat mat = new Mat(src.size(), CvType.CV_16F);
        Core.subtract(src, dst, mat);
//        HighGui.imshow("2", dst);
//        HighGui.waitKey();
        Imgcodecs.imwrite("doc/pic/cvboy_pyr_lap.jpg", mat);
    }

    /**
     * 直方图 均衡化  用于曝光不足的情况下
     */
    public void calc() {
        List<Mat> images = new ArrayList<>(); // 需要统计的图像
        Mat src = Imgcodecs.imread("doc/pic/cvlena.jpg", Imgcodecs.IMREAD_GRAYSCALE);
        Mat dst = new Mat();

        images.add(src);
        MatOfInt channels = new MatOfInt(0); // 统计使用的通道 0表示统计黑白点
        Mat mask = new Mat();
        Mat hist = new Mat(); // 生成的直方图数组
        MatOfInt histSize = new MatOfInt(256); // 统计区间
        MatOfFloat ranges = new MatOfFloat(0, 256); // 像素范围 通常设置为0-256
        // 统计像素点
        Imgproc.calcHist(images, channels, mask, hist, histSize, ranges);
        // 绘制直方图
        int height = 400;
        int width = 512;
        Mat mat = new Mat(height, width, CvType.CV_8UC3, new Scalar(0, 0, 0));
        Core.normalize(hist, dst, 0, mat.rows(), Core.NORM_MINMAX);
        float[] histData = new float[(int) (dst.total() * dst.channels())];
        dst.get(0, 0, histData);

        int binW = (int) Math.round(width * 1.0d / 256);
        for (int i = 0; i < 256; i++) {
            Imgproc.line(mat, new Point(binW * i, height), new Point(binW * i, height - Math.round(histData[i])), new Scalar(255, 255, 255), binW);
        }
        Imgcodecs.imwrite("doc/pic/cvlena_calc.jpg", mat);

        Imgproc.equalizeHist(src, dst);
        Imgcodecs.imwrite("doc/pic/cvlena_equalize.jpg", dst);

    }

    /**
     * 边缘检测  轮廓算法
     * 边缘:图像中亮度或者灰度值有明显变化的像素点集合
     * sobel算法:在每个像素上下左右四个领域灰度值加权差 达到极值即边缘
     * 能达到较好的检测效果 且可以对噪声产生平滑抑制作用   但是边缘较粗且有可能出现伪边缘
     * scharr算法:对sobel算法改进 效果更好
     * 拉普拉斯算法:使用二阶导数计算边缘  对噪声敏感 可能产生双边效果
     * canny算法:设置minval和maxval 高于maxval即为边缘点 处在之间且和边缘点有链接即为边缘点 低于minval不是边缘点
     * 轮廓检测:
     */
    public void edge() {
        Mat src = Imgcodecs.imread("doc/pic/cvboy.jpg", Imgcodecs.IMREAD_GRAYSCALE);
        Mat dst = new Mat();
        // sobel算法
        Imgproc.Sobel(src, dst, -1, 0, 1);
        Imgcodecs.imwrite("doc/pic/cvboy_sobel.jpg", dst);
        // scharr算法
        Imgproc.Scharr(src, dst, -1, 0, 1);
        Imgcodecs.imwrite("doc/pic/cvboy_scharr.jpg", dst);
        // 拉普拉斯算法
        Imgproc.Laplacian(src, dst, 0);
        Imgcodecs.imwrite("doc/pic/cvboy_lap.jpg", dst);
        // canny算法
        Imgproc.Canny(src, dst, 60, 200);
        Imgcodecs.imwrite("doc/pic/cvboy_canny.jpg", dst);
        // 轮廓检测
        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(dst, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
        Mat mat = new Mat(src.height(), src.width(), CvType.CV_8UC3, new Scalar(255, 255, 255));

        for (int i = 0; i < contours.size(); i++) {
            Imgproc.drawContours(mat, contours, i, new Scalar(0, 0, 0), 3);
        }
        Imgcodecs.imwrite("doc/pic/cvlena_contours.jpg", mat);
    }

    /**
     * 模板匹配
     * 模板:和原图像对比的图像
     * 用于在图像中搜素给定模板位置的方法
     * 通过移动模板寻找最大相似度的位置
     * Imgproc.TM_SQDIFF 平方差匹配
     * Imgproc.TM_CCORR 相关匹配
     * Imgproc.TM_CCOEFF 相关系数匹配
     * 使用 Core.minMaxLoc 获取匹配的结果
     * TM_SQDIFF 使用最小值
     * TM_CCORR 使用最大值
     * TM_CCORR 使用最大值
     */
    public void template() {
        Mat src = Imgcodecs.imread("doc/pic/cvlena.jpg");
        Mat dst = new Mat();
        Mat temp = Imgcodecs.imread("doc/pic/cvlenaface.jpg");
        Imgproc.matchTemplate(src, temp, dst, Imgproc.TM_SQDIFF);

        Core.MinMaxLocResult result = Core.minMaxLoc(dst);
        Point loc = result.minLoc;
        Imgproc.rectangle(src, loc, new Point(loc.x + temp.cols(), loc.y + temp.rows()), new Scalar(255, 0, 0));

        Imgcodecs.imwrite("doc/pic/cvlena_match_temp.jpg", src);
    }

    /**
     * 绘制线 绘制圆
     * 霍夫变换
     * 在霍夫空间中统计直线交叉点的次数 即投票
     * 标准霍夫变换 返回极坐标
     * 概率霍夫变换 返回两个端点坐标
     * 霍夫线检测
     * rtho     累加器的距离分辨率   单位像素
     * theta    累加器的角度分辨率 单位弧度
     * thresh   阈值 要判断一条直线最少的度量 数值越大 检测的线就越少
     * 霍夫圆检测
     * method 使用梯度算法 其他算法效率低下
     * dp   霍夫空间分辨率
     * minDst   圆心之间最小间距    如果两个圆心距小于该值则认为它们是同一圆心
     * param1   边缘检测使用canny算法的高阈值   低阈值是高阈值的一半
     * param2   检测圆心和确定半径时共有的阈值
     * minRadius    检测到的圆半径最小值
     * maxRadius    检测到的圆半径最大值
     */
    public void hough() {
        Mat src = Imgcodecs.imread("doc/pic/cvline.jpg", Imgcodecs.IMREAD_GRAYSCALE);
        Mat dst = new Mat();
//        Mat cdst = new Mat();
//        Mat lines = new Mat();
//
//        // canny算法
//        Imgproc.Canny(src, dst, 60, 200, 3, false);
//        // 再将灰度图转成彩色图
//        Imgproc.cvtColor(dst, cdst, Imgproc.COLOR_GRAY2BGR);
//        // 使用霍夫检测 得到线条的极坐标
//        Imgproc.HoughLines(dst, lines, 1, Math.PI / 180, 150);
//        // 在彩色图上绘制检测出来的线
//        for (int x = 0; x < lines.rows(); x++) {
//            double rho = lines.get(x, 0)[0];
//            double theta = lines.get(x, 0)[1];
//            double a = Math.cos(theta);
//            double b = Math.sin(theta);
//            double x0 = a * rho;
//            double y0 = b * rho;
//            Point pt1 = new Point(Math.round(x0 - 1000 * b), Math.round(y0 + 1000 * a));
//            Point pt2 = new Point(Math.round(x0 + 1000 * b), Math.round(y0 - 1000 * a));
//            Imgproc.line(cdst, pt1, pt2, new Scalar(0, 0, 255), 3);
//        }
//        Imgcodecs.imwrite("doc/pic/cvline_hough.jpg", cdst);


        // 获得原图的灰度图使用中值滤波降噪
//        src = Imgcodecs.imread("doc/pic/cvchess.jpg", Imgcodecs.IMREAD_GRAYSCALE);
//        Imgproc.medianBlur(src, dst, 5);

        src = Imgcodecs.imread("doc/pic/cvchess.jpg");
        Imgproc.cvtColor(src, dst, Imgproc.COLOR_BGR2GRAY);
        Imgproc.medianBlur(dst, dst, 5);

        Mat circles = new Mat();
        // 霍夫圆检测算法
        Imgproc.HoughCircles(dst, circles, Imgproc.HOUGH_GRADIENT, dst.rows() * 1.0d / 16, 100, 30, 1, 30);

        // 绘制圆点
        for (int x = 0; x < circles.cols(); x++) {
            long x0 = Math.round(circles.get(0, x)[0]);
            long x1 = Math.round(circles.get(0, x)[1]);
            int radius = (int) Math.round(circles.get(0, x)[2]);
            Point point = new Point(x0, x1);
            Imgproc.circle(src, point, radius, new Scalar(255, 0, 255), 3, 8, 0);
        }
        Imgcodecs.imwrite("doc/pic/cvchess_hough.jpg", src);
    }

    /**
     * 角点检测
     * 角点:图像中边角的部分 特点是像任意方向移动任意位置都会发生明显变化
     * 哈里斯算法
     * 将窗口向各个方向移动(u,v)计算出差异总和
     * 将矩阵M的行列式值与M的轨迹相减    比较该差值和预先设定的阈值
     * 托马斯算法
     * 两个特征值中较小的一个大于最小阈值   得到强角点
     * 两个都具有旋转不变性(即旋转图像后角点仍存在)  不具备尺度不变性(即图像缩放后角点可能消失)
     * image   输入图像 灰度图
     * maxCorners  可以检测到角点数最大值
     * qualityLevel    角点质量等级
     * minDistance     角点之间最小距离
     * mask        指定检测区域  如果检测全图则设置为null
     * blockSize   计算协方矩阵时窗口大小
     * useHarrisDetector   是否使用哈里斯算 false表示使用托马斯算法
     * k   哈里斯角点检测的中间参数  一般使用0.04-0.06 如果上面设置false 该参数无效
     */
    public void cornor() {
        Mat src = Imgcodecs.imread("doc/pic/cvlena.jpg");
        Mat dst = new Mat();

        // 转换成灰度图
        Imgproc.cvtColor(src, dst, Imgproc.COLOR_RGB2GRAY);

        MatOfPoint cornors = new MatOfPoint();
        boolean useHarrisDetector = true;
        Imgproc.goodFeaturesToTrack(dst, cornors, 500, 0.01, 20, new Mat(), 3, useHarrisDetector, 0.04);
        Point[] points = cornors.toArray();
        for (Point point : points) {
            Imgproc.circle(src, point, 4, new Scalar(255, 255, 0), 2);
        }
        Imgcodecs.imwrite("doc/pic/cvlena_cornor.jpg", src);
    }

    /**
     * 角点检测
     * sift算法
     * scale invariant feature transform
     * 该算法补足了哈里斯算法和托马斯算法不具备尺度不变性特征
     * 尺度空间内极值检测 高斯核函数星星滤波
     * 特征点过滤并定位
     * 为特征点分配方向值
     * 生成特征描述子
     * 相关函数
     * SIFT.create(int nfeature)   保留的最佳特征点数量
     * Feature2D.detect(Mat image, MatOfKeyPoint kp)  检测关键点
     * Feature2d.drawKeypoints(image, kp,outimage, color, flag)
     * Feature2d.DrawMatchesFlags_DRAW_RICH_KEYPOINTS      为每一个特征点绘制大小和方向
     * Feature2d.DrawMatchesFlags_NOT_DRAW_SINGLE_POINTS   不绘制单点的特征点
     * <p>
     * FAST 加速芬顿测试特征
     * features from accelerated segment test
     * 如果某个像所的灰度值与周围足够多像素点灰度值不同 那么这就是一个特征点
     * <p>
     * ORB算法 替代 sift 和 surf
     * oriented fasr and rotated brief
     * orb参数说明
     * nfeature        提取特征点最大数量
     * scaleFactor     图像金字塔之间尺度参数
     * nLevel      金字塔层数
     * edgeThreshhold      边缘阈值
     * firstLevel      从第几层开始搜索特征点     一般设置为0
     * wet_k       用于产生brief描述子的个数  一般为2
     * scoreType   特征点排序算法 ORb.HARRIS_SCOPE  ORB.FAST_SCOPE
     * patchSize   计算brief描述子特征点邻域大小
     * fastThreshold       FAST算法阈值
     */
    public void sift() {
        Mat src = Imgcodecs.imread("doc/pic/cvboy.jpg");
        Mat dst = new Mat();

//        sift
        // 转换成灰度图
        Imgproc.cvtColor(src, dst, Imgproc.COLOR_BGR2GRAY);
        // 检测出关键点
        SIFT sift = SIFT.create(500);
        MatOfKeyPoint pt = new MatOfKeyPoint();
        sift.detect(src, pt);
        Features2d.drawKeypoints(src, pt, dst, new Scalar(0, 0, 255), Features2d.DrawMatchesFlags_NOT_DRAW_SINGLE_POINTS);
        Imgcodecs.imwrite("doc/pic/cvboy_sift.jpg", dst);

//        orb
        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
        ORB orb = ORB.create(50, 1.2f, 8, 31, 0, 2, ORB.HARRIS_SCORE, 31, 20);
        orb.detect(gray, pt);
        Features2d.drawKeypoints(src, pt, dst, new Scalar(0, 0, 255), Features2d.DrawMatchesFlags_NOT_DRAW_SINGLE_POINTS);
        Imgcodecs.imwrite("doc/pic/cvboy_orb.jpg", dst);

//        fast
    }

    /**
     * 人脸检测算法
     * 基础是 haar 特征分类器
     * 边缘特征 线特征 四角矩形特征  特征值等于黑色矩形像素和与白色矩形像素和之差
     */
    public void cascade() {
        CascadeClassifier detector = new CascadeClassifier();

        detector.load("doc/opencv/haarcascades/haarcascade_eye.xml");
        cascade("doc/pic/cvard_eye.jpg", detector);

        detector.load("doc/opencv/haarcascades/haarcascade_frontalface_alt.xml");
        cascade("doc/pic/cvard_frontalface_alt.jpg", detector);

        detector.load("doc/opencv/haarcascades/haarcascade_frontalface_alt_tree.xml");
        cascade("doc/pic/cvard_frontalface_alt_tree.jpg", detector);

        detector.load("doc/opencv/haarcascades/haarcascade_frontalface_alt2.xml");
        cascade("doc/pic/cvard_frontalface_alt2.jpg", detector);

        detector.load("doc/opencv/haarcascades/haarcascade_frontalface_default.xml");
        cascade("doc/pic/cvard_frontalface_default.jpg", detector);

        detector.load("doc/opencv/haarcascades/haarcascade_frontalcatface.xml");
        cascade("doc/pic/cvard_frontalcatface.jpg", detector);

        detector.load("doc/opencv/haarcascades/haarcascade_frontalcatface_extended.xml");
        cascade("doc/pic/cvfrontalcatface_extended.jpg", detector);

    }

    private void cascade(String outPath, CascadeClassifier detector) {
        Mat src = Imgcodecs.imread("doc/pic/cvard.jpg");
        MatOfRect result = new MatOfRect();
        detector.detectMultiScale(src, result);
        if (null == result.toArray()) {
            return;
        }
        if (result.toArray().length < 1) {
            return;
        }
        Rect[] rects = result.toArray();
        Scalar redColor = new Scalar(0, 0, 255);
        for (Rect rect : rects) {
            Point p1 = new Point(rect.x, rect.y);
            Point p2 = new Point(rect.x + rect.width, rect.y + rect.height);
            Imgproc.rectangle(src, p1, p2, redColor);
        }
        Imgcodecs.imwrite(outPath, src);
    }

    /**
     * 视频读取和处理
     * 1.创建VideoCapture类
     * 2.打开视频文件
     * 3.确认视频文件是否已打开
     * 4.读取一帧图像
     * 5.显示或处理图像、
     * 6.释放内存
     */
    public void video() {
        VideoCapture vc = new VideoCapture();
        vc.open("doc/video/haha.mp4");
        if (!vc.isOpened()) {
            return;
        }
        Mat frame = new Mat();
        if(!vc.read(frame)) {
            return;
        }
        HighGui.imshow("vc", frame);
        HighGui.waitKey(1);
        if(!vc.grab()) {
            return;
        }
        while (vc.retrieve(frame)) {
            HighGui.imshow("vc", frame);
            HighGui.waitKey(1);
            if (!vc.grab()) {
                System.out.println("grab over!");
                HighGui.destroyWindow("vc");
                break;
            }
        }
        System.out.println("vc release!");
        vc.release();
    }
}