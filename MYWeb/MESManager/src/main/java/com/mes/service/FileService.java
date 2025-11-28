package com.mes.service;

import com.alibaba.fastjson.JSONObject;
import com.mes.domain.User;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;

//业务逻辑层接口类
public interface FileService {

    JSONObject fileUpload(ArrayList<MultipartFile> uploadFiles, HttpServletRequest request);
}
