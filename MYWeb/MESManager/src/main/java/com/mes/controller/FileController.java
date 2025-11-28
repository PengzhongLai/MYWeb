package com.mes.controller;

import com.alibaba.fastjson.JSONObject;
import com.mes.domain.Product;
import com.mes.service.FileService;
import com.mes.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Api(tags = "文件管理",description = "文件上传下载")
@RestController
@RequestMapping("/file") //所有接口的父级路径
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 使用接口测试工具调用选择body中form-data上传，KEY值选择File文件类型
     * **/
    @ApiOperation(value = "文件上传",notes = "文件上传")
    @PostMapping("/file-upload")
    public  ResponseEntity<?>   fileUpload(@RequestParam("file")ArrayList<MultipartFile> uploadFiles, HttpServletRequest request){
        //定义列表jsonObject保存提示信息
        JSONObject jsonObject=this.fileService.fileUpload(uploadFiles,request);
        return ResponseEntity.ok(jsonObject);
    }


    @ApiOperation(value = "文件下载",notes = "实现的文件下载功能")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name="fileName",value = "要下载的文件名称",required = true,paramType = "query",dataType = "String"),
    })


    @GetMapping("/file-download")
    public ResponseEntity<byte[]> fileDownload(@RequestParam HashMap params, HttpServletRequest request) {
        try {
            // 使用与上传一致的基础路径
            String basePath = request.getServletContext().getRealPath("/images/productImg/");
            String fileName = params.get("fileName") != null ? params.get("fileName").toString() : "";
            if (fileName.isEmpty()) {
                return null;
            }

            String filePath = basePath + fileName;
            File file = new File(filePath);
            if (!file.exists()) {
                return null; // 文件不存在
            }

            byte[] bytes = FileUtils.readFileToByteArray(file);
            HttpHeaders headers = new HttpHeaders();
            fileName = URLEncoder.encode(fileName, "UTF-8");
            headers.setContentDispositionFormData("attachment", fileName);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
