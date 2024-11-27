package com.tequeno.vivaboot.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

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

    private Map<String, Long> suffixMap;

    @Value("${file.upload}")
    protected String uploadPath;

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

        File dirFile = new File(uploadPath);
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
        String outFilePath = String.format("%s/%d.%s", uploadPath, System.currentTimeMillis(), suffix);
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
            String outFilePath = String.format("%s/%d.%s", uploadPath, System.currentTimeMillis(), suffix);
            File outFile = new File(outFilePath);
            file.transferTo(outFile); // throws IOException
            list.add(outFilePath);
        }
        return list;
    }
}
