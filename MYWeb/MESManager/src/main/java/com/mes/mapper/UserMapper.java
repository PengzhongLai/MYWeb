package com.mes.mapper;

import com.alibaba.fastjson.JSONObject;
import com.mes.domain.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;

public interface UserMapper {
    //根据账号密码查询用信息
    int queryUserByLoginId(HashMap params);

    //根据账号查询用户基本信息
    User queryUserInfo(HashMap params);

    //新增一条用户信息（保存账号和密码到tb_sys_user表）
    int  addUser(User user);

    //新增一条用户信息（保存用户基本信息到tb_sys_user_staff表）
    int  addUserStaff(User user);

    //根据账号修改一条用户信息（保存密码到tb_sys_user表）
    int updateUser(User user);

    //根据账号修改一条用户信息（保存用户基本信息到tb_sys_user_staff表）
    int  updateUserStaff(User user);

    //根据账号删除一条tb_sys_user表中数据
    int deleteUserByLoginId(HashMap params);

    //根据账号删除一条tb_sys_user_staff表中数据
    int  deleteUserStaffByLoginId(HashMap params);
}
