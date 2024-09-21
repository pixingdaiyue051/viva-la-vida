package com.tequeno.vivaboot.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Component
public class FileUploader {

    private final static Logger log = LoggerFactory.getLogger(FileUploader.class);

    private final static String MAX = "10M";

    private final static long MAX_SIZE = 10496246L; // 10.01M = 10.01*1024*1024=10496246

    private Map<String, String> suffixMap;

    @Value("${file.upload}")
    protected String uploadPath;

    @PostConstruct
    private void init() {
        suffixMap = new HashMap<>(11);
        suffixMap.put("jpg", "jpg");
        suffixMap.put("jpeg", "jpeg");
        suffixMap.put("png", "png");
        suffixMap.put("bmp", "bmp");
        suffixMap.put("gif", "gif");

        suffixMap.put("doc", "doc");
        suffixMap.put("docx", "docx");
        suffixMap.put("pdf", "pdf");
        suffixMap.put("xls", "xls");
        suffixMap.put("xlsx", "xlsx");
        suffixMap.put("txt", "txt");


        File dirFile = new File(uploadPath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
    }


    public String upload(CommonsMultipartFile file) throws Exception {
        String filename = file.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf(".") + 1);
        if (!suffixMap.containsKey(suffix)) {
            throw new RuntimeException(String.format("只能上传%s", suffixMap.values()));
        }
        if (file.getSize() > MAX_SIZE) {
            throw new RuntimeException(String.format("[%s]大小超出%s", filename, MAX));
        }
        String outFilePath = String.format("%s%d.%s", uploadPath, System.currentTimeMillis(), suffix);
        File outFile = new File(outFilePath);
        file.transferTo(outFile); // throws IOException
        return outFilePath;
    }

    public List<String> upload(List<CommonsMultipartFile> fileList) throws Exception {
        for (CommonsMultipartFile file : fileList) {
            String filename = file.getOriginalFilename();
            String suffix = filename.substring(filename.lastIndexOf(".") + 1);
            if (!suffixMap.containsKey(suffix)) {
                throw new RuntimeException(String.format("只能上传%s", suffixMap.values()));
            }
            if (file.getSize() > MAX_SIZE) {
                throw new RuntimeException(String.format("[%s]大小超出%s", filename, MAX));
            }
        }
        List<String> list = new ArrayList<>();
        for (CommonsMultipartFile file : fileList) {
            String filename = file.getOriginalFilename();
            String suffix = filename.substring(filename.lastIndexOf(".") + 1);
            String outFilePath = String.format("%s%d.%s", uploadPath, System.currentTimeMillis(), suffix);
            File outFile = new File(outFilePath);
            file.transferTo(outFile); // throws IOException
            list.add(outFilePath);
        }
        return list;
    }
}
