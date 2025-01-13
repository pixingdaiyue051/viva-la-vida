package com.tequeno.vivaboot.controller;

import com.tequeno.dto.HtResultModel;
import com.tequeno.utils.HtResultUtil;
import com.tequeno.vivaboot.service.FileHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("file")
@Slf4j
public class FileController {

    @Resource
    private FileHandler fileHandler;

    @RequestMapping("upload/single")
    public HtResultModel uploadSingle(MultipartFile file) {
        try {
            String out = fileHandler.upload(file);
            return HtResultUtil.success(out);
        } catch (Exception e) {
            log.error("保存文件[{}]失败:", file.getOriginalFilename(), e);
            return HtResultUtil.fail(e.getMessage());
        }
    }

    @RequestMapping("upload/multi")
    public HtResultModel uploadMulti(List<MultipartFile> fileList) {
        try {
            List<String> out = fileHandler.upload(fileList);
            return HtResultUtil.success(out);
        } catch (Exception e) {
            log.error("保存文件失败:", e);
            return HtResultUtil.fail(e.getMessage());
        }
    }

    @RequestMapping("export")
    public HtResultModel export(HttpServletResponse response) {
        try {
            fileHandler.export(response);
            return HtResultUtil.success("导出成功");
        } catch (Exception e) {
            log.error("导出失败", e);
            return HtResultUtil.fail(e.getMessage());
        }
    }

    @RequestMapping("download")
    public HtResultModel download(String fileName, HttpServletResponse response) {
        try {
            fileHandler.download(fileName, response);
            return HtResultUtil.success("下载成功");
        } catch (Exception e) {
            log.error("下载失败", e);
            return HtResultUtil.fail(e.getMessage());
        }
    }
}
