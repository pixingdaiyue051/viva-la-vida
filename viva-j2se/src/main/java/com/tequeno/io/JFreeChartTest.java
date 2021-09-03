package com.tequeno.io;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.TextAnchor;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;

public class JFreeChartTest {

    public static void main(String[] args) throws Exception {
        //创建主题样式
        StandardChartTheme mChartTheme = new StandardChartTheme("CN");
        //设置标题字体
        mChartTheme.setExtraLargeFont(new Font("黑体", Font.BOLD, 20));
        //设置轴向字体
        mChartTheme.setLargeFont(new Font("宋体", Font.PLAIN, 15));
        //设置图例字体
        mChartTheme.setRegularFont(new Font("宋体", Font.PLAIN, 15));
        //应用主题样式
        ChartFactory.setChartTheme(mChartTheme);
        bar();
        pie();
    }

    private static void bar() throws Exception {

        JFreeChart chart = ChartFactory.createBarChart("hi", "情绪",
                "会见数量", getBarDataset(), PlotOrientation.VERTICAL, true, true, false);
        chart.setTitle(new TextTitle("心态晴雨表", new Font("宋体", Font.BOLD + Font.ITALIC, 20)));

        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setBaseItemLabelsVisible(true);
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));

        renderer.setMaximumBarWidth(0.10D);//显示每个柱的最大宽度
        renderer.setMinimumBarLength(1.0D);//最短的柱的长度,避免数值太小而显示不出
        renderer.setItemMargin(0.2D);//每个柱之间的间距

        renderer.setSeriesPaint(0, new Color(255, 227, 96));
        renderer.setSeriesPaint(1, new Color(132, 200, 255));
        renderer.setSeriesPaint(2, new Color(170, 91, 251));
        renderer.setSeriesPaint(3, new Color(70, 92, 223));
        renderer.setSeriesPaint(4, new Color(116, 48, 60));
        renderer.setSeriesPaint(5, new Color(230, 60, 60));
        renderer.setSeriesPaint(6, new Color(53, 52, 87));

        OutputStream os = new FileOutputStream("/data/doc/202108/img_bar.jpeg");//图片是文件格式的，故要用到FileOutputStream用来输出。
        ChartUtilities.writeChartAsJPEG(os, chart, 600, 500);


        os.close();//关闭输出流
    }

    private static void pie() throws Exception {
        JFreeChart chart = ChartFactory.createPieChart("心态晴雨表", getPieDataset(), true, true, false);
        chart.setTitle(new TextTitle("心态晴雨表", new Font("宋体", Font.BOLD + Font.ITALIC, 20)));

//        饼图标签显示百分比方法
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{2}", new DecimalFormat("0.0"), new DecimalFormat("0.00%")));
        plot.setSectionPaint( "高兴", new Color(255, 227, 96));
        plot.setSectionPaint( "平静", new Color(132, 200, 255));
        plot.setSectionPaint( "惊讶", new Color(170, 91, 251));
        plot.setSectionPaint( "伤心", new Color(70, 92, 223));
        plot.setSectionPaint( "厌恶", new Color(116, 48, 60));
        plot.setSectionPaint( "愤怒", new Color(230, 60, 60));
        plot.setSectionPaint( "恐惧", new Color(53, 52, 87));

        OutputStream os = new FileOutputStream("/data/doc/202108/img_pie.jpeg");//图片是文件格式的，故要用到FileOutputStream用来输出。
        ChartUtilities.writeChartAsJPEG(os, chart, 600, 500);

        os.close();//关闭输出流
    }

    public static CategoryDataset getBarDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.setValue(25, "高兴", "高兴");
        dataset.setValue(25, "平静", "平静");
        dataset.setValue(40, "惊讶", "惊讶");
        dataset.setValue(10, "伤心", "伤心");
        dataset.setValue(10, "厌恶", "厌恶");
        dataset.setValue(10, "愤怒", "愤怒");
        dataset.setValue(10, "恐惧", "恐惧");
        return dataset;
    }

    private static DefaultPieDataset getPieDataset() {
        DefaultPieDataset dpd = new DefaultPieDataset(); //建立一个默认的饼图
        dpd.setValue("高兴", 25);
        dpd.setValue("平静", 25);
        dpd.setValue("惊讶", 40);
        dpd.setValue("伤心", 40);
        dpd.setValue("厌恶", 40);
        dpd.setValue("愤怒", 40);
        dpd.setValue("恐惧", 10);
        return dpd;
    }

//    高兴=100、平静=90、惊讶=80、伤心=70、厌恶=60、愤怒=50、恐惧=40

}