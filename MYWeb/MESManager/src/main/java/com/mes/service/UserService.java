package com.mes.service;

import com.alibaba.fastjson.JSONObject;
import com.mes.domain.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

//业务逻辑层接口类
public interface UserService {

    //根据账号查询用信息
     int queryUserByLoginId(HashMap params);
     //根据账号查询用户基本信息
     User queryUserInfo(HashMap params);
     //新增一条用户信息
     int  addUserInfo(User user);
     //根据账号修改用户信息
     int  updateUserInfo(User user);
     //根据账号删除一条用户信息
     int deleteUserInfoByLoginId(HashMap params);
}
