package com.psl.controller;

import com.psl.pojo.Result;
import com.psl.utils.AliOssUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {

    @Resource
    private AliOssUtil aliOssUtil;

    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws Exception {
        String url = aliOssUtil.uploadFile(file);
        return Result.success(url);
    }
}
