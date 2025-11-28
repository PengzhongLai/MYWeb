package com.mes.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public interface FileService {
    JSONObject fileUpload(ArrayList<MultipartFile> uploadFiles, HttpServletRequest request);
}