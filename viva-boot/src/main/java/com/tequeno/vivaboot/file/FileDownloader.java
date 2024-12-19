package com.tequeno.vivaboot.file;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

/**
 *
 */
@Component
public class FileDownloader {

    private final static String ENC = "UTF-8";

    private final static int BYTE_LEN = 1024;

    @Value("${file.upload}")
    protected String uploadPath;

    @Value("${file.template}")
    protected String template;

    public void export(String fileName, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String filePath = template + fileName;
        File file = new File(filePath);
        if (!file.exists()) {
            throw new RuntimeException("文件不存在");
        }
        if (!file.isFile()) {
            throw new RuntimeException("不是文件,无法读取");
        }
        fileName = file.getName();

        int pointIdx = fileName.lastIndexOf(".");
        String suffix = fileName.substring(pointIdx + 1);

        try (ServletOutputStream outputStream = response.getOutputStream();
             XSSFWorkbook workbook = new XSSFWorkbook(filePath)) {

            fileName = URLEncoder.encode(fileName.substring(0, pointIdx), ENC);
            response.setHeader("Content-disposition", String.format("attachment; filename=%s_%d.%s", fileName, System.currentTimeMillis(), suffix));
            response.setContentType(request.getServletContext().getMimeType(filePath));
            workbook.write(outputStream);
            outputStream.flush();
        }
    }

    public void download(String fileName, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String filePath = uploadPath + fileName;
        File file = new File(filePath);
        if (!file.exists()) {
            throw new RuntimeException("文件不存在");
        }
        if (!file.isFile()) {
            throw new RuntimeException("不是文件,无法读取");
        }
        fileName = file.getName();

        int pointIdx = fileName.lastIndexOf(".");
        String suffix = fileName.substring(pointIdx + 1);

        try (ServletOutputStream os = response.getOutputStream();
             FileInputStream fis = new FileInputStream(filePath)) {

            fileName = URLEncoder.encode(fileName.substring(0, pointIdx), ENC);
            response.setHeader("Content-disposition", String.format("attachment; filename=%s_%d.%s", fileName, System.currentTimeMillis(), suffix));
            response.setContentType(request.getServletContext().getMimeType(filePath));

            byte[] b = new byte[BYTE_LEN];
            int len;
            while ((len = fis.read(b)) > 0) {
                os.write(b, 0, len);
            }
            os.flush();
        }
    }

}
