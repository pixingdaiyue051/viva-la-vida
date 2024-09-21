package com.tequeno.vivaboot.file;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final static Logger log = LoggerFactory.getLogger(FileDownloader.class);

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

        ServletOutputStream outputStream = response.getOutputStream();
        int pointIdx = fileName.lastIndexOf(".");
        String suffix = fileName.substring(pointIdx + 1);
        fileName = URLEncoder.encode(fileName.substring(0, pointIdx), ENC);
        response.setHeader("Content-disposition", String.format("attachment; filename=%s%d.%s", fileName, System.currentTimeMillis(), suffix));
        response.setContentType(request.getServletContext().getMimeType(filePath));

        XSSFWorkbook workbook = new XSSFWorkbook(filePath);
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
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

        ServletOutputStream outputStream = response.getOutputStream();
        int pointIdx = fileName.lastIndexOf(".");
        String suffix = fileName.substring(pointIdx + 1);
        fileName = URLEncoder.encode(fileName.substring(0, pointIdx), ENC);
        response.setHeader("Content-disposition", String.format("attachment; filename=%s%d.%s", fileName, System.currentTimeMillis(), suffix));
        response.setContentType(request.getServletContext().getMimeType(filePath));

        FileInputStream fis = new FileInputStream(filePath);
        byte[] b = new byte[BYTE_LEN];
        while (fis.read(b) != -1) {
            outputStream.write(b);
        }
        fis.close();
        outputStream.flush();
        outputStream.close();
    }

}
