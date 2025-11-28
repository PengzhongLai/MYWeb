package com.mes.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mes.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public JSONObject fileUpload(ArrayList<MultipartFile> uploadFiles, HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        try {
            // 获取项目中 /images/productImg 目录的实际物理路径
            String basePath = request.getServletContext().getRealPath("/images/productImg/");
            File directory = new File(basePath);

            // 目录不存在则创建（包括多级目录）
            if (!directory.exists()) {
                boolean isCreated = directory.mkdirs();
                if (!isCreated) {
                    jsonObject.put("code", -1);
                    jsonObject.put("msg", "目录创建失败：" + basePath);
                    return jsonObject;
                }
            }

            List<String> fileRelativePaths = new ArrayList<>(); // 存储前端可访问的相对路径

            // 循环处理上传的文件
            for (MultipartFile file : uploadFiles) {
                if (file.isEmpty() || file.getSize() <= 0) {
                    continue; // 跳过空文件
                }

                // 处理文件名：生成唯一ID避免覆盖，保留原文件后缀
                String originalFileName = file.getOriginalFilename();
                String fileExt = originalFileName.substring(originalFileName.lastIndexOf(".")); // 如 .png
                String uniqueFileName = UUID.randomUUID().toString() + fileExt; // 唯一文件名

                // 构建完整物理路径
                String physicalFilePath = basePath + uniqueFileName;

                try {
                    // 保存文件到服务器
                    file.transferTo(new File(physicalFilePath));
                    // 存储前端可访问的相对路径（用于数据库存储和前端展示）
                    String relativePath = "/images/productImg/" + uniqueFileName;
                    fileRelativePaths.add(relativePath);
                } catch (IOException e) {
                    e.printStackTrace();
                    jsonObject.put("code", -1);
                    jsonObject.put("msg", "文件保存失败：" + originalFileName);
                    return jsonObject;
                }
            }

            // 处理上传结果
            if (fileRelativePaths.isEmpty()) {
                jsonObject.put("code", -1);
                jsonObject.put("msg", "未上传有效文件");
            } else {
                jsonObject.put("code", 1);
                jsonObject.put("data", fileRelativePaths); // 返回所有成功上传的文件相对路径
                jsonObject.put("msg", "上传成功，共" + fileRelativePaths.size() + "个文件");
            }

        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put("code", -1);
            jsonObject.put("msg", "上传异常：" + e.getMessage());
        }

        return jsonObject;
    }
}