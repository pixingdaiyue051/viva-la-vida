package com.tequeno.vivaboot.file;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FileHandler {

    public final static String FILE_PATH = "/data/upload/";

    private Map<String, Long> suffixMap;

    @PostConstruct
    private void init() {
        suffixMap = new HashMap<>(17);
        suffixMap.put("jpg", 4204790L);
        suffixMap.put("jpeg", 4204790L);
        suffixMap.put("png", 4204790L);
        suffixMap.put("bmp", 4204790L);
        suffixMap.put("gif", 4204790L); // 4.01M = 4.01*1024*1024=4204790

        suffixMap.put("txt", 4204790L); // 4.01M = 4.01*1024*1024=4204790
        suffixMap.put("pdf", 10496246L);
        suffixMap.put("doc", 10496246L);
        suffixMap.put("docx", 10496246L);
        suffixMap.put("ppt", 10496246L);
        suffixMap.put("pptx", 10496246L);
        suffixMap.put("xls", 10496246L);
        suffixMap.put("xlsx", 10496246L); // 10.01M = 10.01*1024*1024=10496246

        suffixMap.put("zip", 104868085L);
        suffixMap.put("7z", 104868085L); // 100.01M = 10.01*1024*1024=104868085

        File dirFile = new File(FILE_PATH);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
    }


    public String upload(MultipartFile file) throws Exception {
        String filename = file.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf(".") + 1);
        if (!suffixMap.containsKey(suffix)) {
            throw new RuntimeException(String.format("只能上传%s", suffixMap.keySet()));
        }
        if (file.getSize() > suffixMap.get(suffix)) {
            throw new RuntimeException(String.format("[%s]大小超出[%d]kb", filename, suffixMap.get(suffix)));
        }
        String outFilePath = String.format("%s/%d.%s", FILE_PATH, System.currentTimeMillis(), suffix);
        File outFile = new File(outFilePath);
        file.transferTo(outFile); // throws IOException
        return outFilePath;
    }

    public List<String> upload(List<MultipartFile> fileList) throws Exception {
        for (MultipartFile file : fileList) {
            String filename = file.getOriginalFilename();
            String suffix = filename.substring(filename.lastIndexOf(".") + 1);
            if (!suffixMap.containsKey(suffix)) {
                throw new RuntimeException(String.format("只能上传%s", suffixMap.keySet()));
            }
            if (file.getSize() > suffixMap.get(suffix)) {
                throw new RuntimeException(String.format("[%s]大小超出[%d]kb", filename, suffixMap.get(suffix)));
            }
        }
        List<String> list = new ArrayList<>();
        for (MultipartFile file : fileList) {
            String filename = file.getOriginalFilename();
            String suffix = filename.substring(filename.lastIndexOf(".") + 1);
            String outFilePath = String.format("%s/%d.%s", FILE_PATH, System.currentTimeMillis(), suffix);
            File outFile = new File(outFilePath);
            file.transferTo(outFile); // throws IOException
            list.add(outFilePath);
        }
        return list;
    }

    public void export(HttpServletResponse response) throws Exception {
        try (XSSFWorkbook wb = new XSSFWorkbook();
             OutputStream os = response.getOutputStream()) {

            XSSFSheet sheet1 = wb.createSheet();
            XSSFRow row1 = sheet1.createRow(0);

            XSSFCell cell00 = row1.createCell(0);
            cell00.setCellValue("编号");
            XSSFCell cell01 = row1.createCell(1);
            cell01.setCellValue("姓名");
            XSSFCell cell02 = row1.createCell(2);
            cell02.setCellValue("年龄");

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader("Content-Disposition", String.format("attachment; filename=%d.xlsx", System.currentTimeMillis()));
            response.flushBuffer();
            wb.write(os);
            os.flush();
        }
    }

    public void download(String fileName, HttpServletResponse response) throws Exception {
        String filePath = FILE_PATH + fileName;
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

            fileName = URLEncoder.encode(fileName.substring(0, pointIdx), StandardCharsets.UTF_8);
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader("Content-Disposition", String.format("attachment; filename=%s_%d.%s", fileName, System.currentTimeMillis(), suffix));
            response.flushBuffer();
            byte[] b = new byte[1024];
            int len;
            while ((len = fis.read(b)) > 0) {
                os.write(b, 0, len);
            }
            os.flush();
        }
    }
}
