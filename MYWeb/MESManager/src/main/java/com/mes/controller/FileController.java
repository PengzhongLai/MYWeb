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
  public ResponseEntity<byte[]> fileDownload(@RequestParam HashMap params){
        try {
            //定义要下载文件的目录
            String path="D:\\uploadFile\\";
            //判断文件名称是否存在
            String fileName="";
            if(params.get("fileName")!=null) {
                 fileName = params.get("fileName").toString();
            }else {
                return null;//要下载的文件名称为空，无法下载文件，返回null
            }
            //拼接要下载文件的全路径
            String filePath=path + fileName;
            //创建该文件的对象
            File file=new File(filePath);
            //将文件读取到字节数组中
           byte[] bytes= FileUtils.readFileToByteArray(file);
           //创建headers对象，设置响应头信息
            HttpHeaders headers=new HttpHeaders();
            //设置浏览器以“utf-8”编码方式显示文件名
            fileName= URLEncoder.encode(fileName,"UTF-8");
            //设置下载的方式的为打开文件,并指定文件名称
            headers.setContentDispositionFormData("attachment",fileName);
            //设置文件的下载方式二进制流
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return new ResponseEntity<byte[]>(bytes,headers, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
  }

}
