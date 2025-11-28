package com.mes.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mes.domain.Product;
import com.mes.mapper.ProductMapper;
import com.mes.service.FileService;
import com.mes.service.ProductService;
import com.mes.util.QrcodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//业务逻辑层接口实现类
@Service //@Service注解是Spring框架中用于标注业务逻辑层的实现类
public class FileServiceImpl implements FileService {

  @Override
  public   JSONObject fileUpload(ArrayList<MultipartFile> uploadFiles, HttpServletRequest request){
        JSONObject jsonObject=new JSONObject();
        try {
            //指定目录，将上传的文件保存到该目录下
            String path="D:\\uploadFile\\";
            //判断是否有上传文件
            if(uploadFiles.get(0).getSize()>0){

                File directory=new File(path);
                //判断指定目录是否存在，不存在则创建目录
                if(!directory.exists()){
                    //指定目录不存在则创建目录
                    boolean boo=directory.mkdirs();
                    if(boo){
                        System.out.println("===目录创建成功，"+path);
                    }else {
                        System.out.println("===目录创建失败，"+path);
                    }

                }else {
                    System.out.println("===指定的目录已存在，跳过闯将！");
                }
                //循环获取文件
                for(MultipartFile file:uploadFiles){
                    //判断文件不为空且大小大于o
                    if(!file.isEmpty()&&file.getSize()>0){
                        //获取文件原始名称
                        String originalFileName=file.getOriginalFilename();
                        //设置文件保存到指定的目录路径
                        String filePath=path+originalFileName;
                        try {
                            //保存文件内容到指定的目录路径
                            file.transferTo(new File(filePath));
                        }catch (IOException e){
                            e.printStackTrace();
                            jsonObject.put("msg",filePath+"保存失败！");
                        }
                        jsonObject.put("msg",filePath+"保存成功！");
                    }
                }
            }else {
                jsonObject.put("msg","上传文件失败");
            }
            request.setAttribute("msg",jsonObject);
            //调用接口生成带logo的二维码
            //二维码图片存放路径
            String QRClogCodePath=path+"qr_code.png";
            // 二维码logo图片路径
            String logoImgPath=path+"logo.png";
            //二维码附带的信息
            String text="软件技术3班";
            QrcodeGenerator.qrcodeGenerator(QRClogCodePath,logoImgPath,text);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  jsonObject;
    }

}
