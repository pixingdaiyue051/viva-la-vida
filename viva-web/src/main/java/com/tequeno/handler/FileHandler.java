package com.tequeno.handler;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class FileHandler {

    public final static String PATH = "/data/upload";

    public void initDir() {
        Path path = Paths.get(PATH);
        boolean exists = Files.exists(path);
        if (!exists) {
            try {
                Files.createDirectories(path);
                log.info("上传目录已创建");
            } catch (IOException e) {
                log.error("创建上传目录失败", e);
            }
        }
    }

    public List<File> query() {
        try {
            Stream<Path> pathStream = Files.list(Paths.get(PATH));
            return pathStream.map(Path::toFile).limit(10).collect(Collectors.toList());
        } catch (IOException e) {
            log.error("读文件列表失败", e);
            return List.of();
        }
    }

    public void uploadFile(Part p) throws Exception {
        try (InputStream is = p.getInputStream();
             OutputStream os = Files.newOutputStream(Paths.get(PATH, p.getSubmittedFileName()), StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            byte[] b = new byte[1024];
            int len;
            while ((len = is.read(b)) > 0) {
                os.write(b, 0, len);
            }
        }
    }

    public void downloadFile(HttpServletResponse resp, String fileName) throws Exception {
        try (InputStream is = Files.newInputStream(Paths.get(PATH, fileName), StandardOpenOption.READ);
             ServletOutputStream os = resp.getOutputStream()) {

            resp.setContentType("application/octet-stream");
            resp.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));
            byte[] b = new byte[1024];
            int len;
            while ((len = is.read(b)) > 0) {
                os.write(b, 0, len);
            }
            os.flush();
        }
    }

    public static FileHandler getInstance() {
        return UploadInnerHolder.INSTANCE;
    }

    private static class UploadInnerHolder {
        private final static FileHandler INSTANCE = new FileHandler();
    }
}
