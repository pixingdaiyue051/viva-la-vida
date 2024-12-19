package com.tequeno.vivaboot.file;

import com.tequeno.dto.HtResultModel;
import com.tequeno.utils.HtResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("file")
public class FileController {

    private final static Logger log = LoggerFactory.getLogger(FileController.class);

    @Resource
    private FileUploader fileUploader;

    @Resource
    private FileDownloader fileDownloader;

    @RequestMapping("upload/single")
    public HtResultModel uploadSingle(MultipartFile file) {
        try {
            String out = fileUploader.upload(file);
            return HtResultUtil.success(out);
        } catch (Exception e) {
            log.error("保存文件[{}]失败:", file.getOriginalFilename(), e);
            return HtResultUtil.fail(e.getMessage());
        }
    }

    @RequestMapping("upload/multi")
    public HtResultModel uploadMulti(List<MultipartFile> fileList) {
        try {
            List<String> out = fileUploader.upload(fileList);
            return HtResultUtil.success(out);
        } catch (Exception e) {
            log.error("保存文件失败:", e);
            return HtResultUtil.fail(e.getMessage());
        }
    }

    @RequestMapping("export")
    public HtResultModel export(String fileName, HttpServletRequest request, HttpServletResponse response) {
        try {
            fileDownloader.export(fileName, request, response);
        } catch (Exception e) {
            log.error("导出失败", e);
            return HtResultUtil.fail(e.getMessage());
        }
        return HtResultUtil.success("导出成功");
    }

    @RequestMapping("download")
    public HtResultModel download(String fileName, HttpServletRequest request, HttpServletResponse response) {
        try {
            fileDownloader.download(fileName, request, response);
        } catch (Exception e) {
            log.error("下载失败", e);
            return HtResultUtil.fail(e.getMessage());
        }
        return HtResultUtil.success("下载成功");
    }
}
