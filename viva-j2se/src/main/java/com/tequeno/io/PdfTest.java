package com.tequeno.io;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PdfTest {

    private final static String PDF_PATH_TEMP = "/data/doc/%s/%s.pdf";

    private final static String PDF_IMG_TEMP = "/data/doc/%s/%s.jpeg";

    private final static String PDF_NAME_TEMP = "%s%s月度可视电话报告";

    private final static String ADVICE_TEMP = "本月实际会见的服刑人员占在押服刑人员比例为:%s,其中正面情绪占比:%s," +
            "分析得出有%s服刑人员在会见后情绪有明显转好,有利于改造学习;" +
            "有%s服刑人员会见情绪处于较负面状态,建议重点关注,名单附后.";

    public void run(Map<String, Object> map) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String month = sdf.format(new Date());

        // 阳光指数之和
        Double sunIndex = (Double) map.get("sunIndex");
        // 本月会见量
        Integer meetCount = (Integer) map.get("meetCount");
        // 本月会见犯人量
        Integer totalCriCount = (Integer) map.get("totalCriCount");
        // 情绪统计
        java.util.List<Map<String, Integer>> emotionList = (List<Map<String, Integer>>) map.get("emotionList");
        // 表现积极的犯人数量
        Integer criCount = (Integer) map.get("criCount");
        String prisonName = (String) map.get("prisonName");

        String pdfName = String.format(PDF_NAME_TEMP, month, prisonName);
        String pdfPath = String.format(PDF_PATH_TEMP, month, pdfName);
        String imgPath1 = String.format(PDF_IMG_TEMP, month, "img_bar");
        String imgPath2 = String.format(PDF_IMG_TEMP, month, "img_pie");
        double v1 = criCount / 1D / totalCriCount;
        double v2 = criCount / 1D / meetCount;
        double v3 = sunIndex / 1D / meetCount;
        DecimalFormat percent = new DecimalFormat("0.00%");
        DecimalFormat percent1 = new DecimalFormat("0.000");
        String advice = String.format(ADVICE_TEMP, "10%", percent.format(v2), percent.format(v1), percent.format(1 - v1));

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
            document.open();


            PdfPTable table1 = new PdfPTable(1);
            setContent(table1, pdfName);

            PdfPTable table2 = new PdfPTable(2);
            setContent(table2, "阳光指数");
            setContent(table2, percent1.format(v3));
            setContent(table2, "会见量");
            setContent(table2, meetCount);

            PdfPTable table3 = new PdfPTable(1);
            setImg(table3, imgPath1);
            setImg(table3, imgPath2);

            PdfPTable table4 = new PdfPTable(1);
            setContent(table4, "建议");
            setContent(table4, advice);


            PdfPTable table = new PdfPTable(1);
            table.setKeepTogether(true);
            table.setSplitLate(false);

            PdfPCell c1 = new PdfPCell();
            c1.setBorder(0);
            c1.addElement(table1);

            PdfPCell c2 = new PdfPCell();
            c2.setBorder(0);
            c2.addElement(table2);

            PdfPCell c3 = new PdfPCell();
            c3.setBorder(0);
            c3.addElement(table3);

            PdfPCell c4 = new PdfPCell();
            c4.setBorder(0);
            c4.addElement(table4);

            table.addCell(c1);
            table.addCell(c2);
            table.addCell(c3);
            table.addCell(c4);

            document.add(table);
            document.close();
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setContent(PdfPTable table, Object content) throws IOException, DocumentException {
        Paragraph pp = new Paragraph();
        pp.setAlignment(1);
        pp.setSpacingBefore(15f);
        FontSelector selector = new FontSelector();
        //中文简体
        Font chnFont = FontFactory.getFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        selector.addFont(chnFont);
        Phrase ph = selector.process(content.toString());
        pp.add(ph);

        PdfPCell cell = new PdfPCell();
        cell.setBorderWidthLeft(0);
        cell.setBorderWidthRight(0);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);//然并卵
        cell.setPaddingTop(-2f);//把字垂直居中
        cell.setPaddingBottom(8f);//把字垂直居中
        cell.addElement(pp);
        table.addCell(cell);
    }

    private void setImg(PdfPTable table, String imgPath) throws IOException, BadElementException {
        Image img = Image.getInstance(imgPath);
        PdfPCell cell = new PdfPCell(img, true);
        cell.setBorderColor(BaseColor.GREEN);
//        cell.setPaddingLeft(5);
//        cell.setFixedHeight(60);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
    }

    public static void main(String[] args) {
        PdfTest p = new PdfTest();
        Map<String, Object> hashMap = new HashMap<>(11);
        hashMap.put("prisonName", "A监狱");
        hashMap.put("sunIndex", 3391.8D);
        hashMap.put("meetCount", 300);
        hashMap.put("totalCriCount", 278);
        hashMap.put("criCount", 71);
        try {
            p.run(hashMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

