package com.mes.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mes.domain.User;
import com.mes.mapper.UserMapper;
import com.mes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;


//业务逻辑层接口实现类
@Service //@Service注解是Spring框架中用于标注业务逻辑层的实现类
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public int queryUserByLoginId(HashMap params) {
        // 1. 严格参数校验：确保loginId和password不为空且非空字符串
        if (params == null) {
            System.out.println("==登录参数为空，登录失败==");
            return -1;
        }
        // 提取参数并去除首尾空格
        String loginId = (params.get("loginId") != null) ? params.get("loginId").toString().trim() : "";
        String password = (params.get("password") != null) ? params.get("password").toString().trim() : "";

        if (loginId.isEmpty() || password.isEmpty()) {
            System.out.println("==账号或密码为空，登录失败==");
            return -1;
        }

        // 2. 数据库查询：使用try-catch捕获所有异常（如SQL错误、数据库连接失败等）
        try {
            System.out.println("==接收到的登录参数==");
            System.out.println("账号：" + loginId);
            System.out.println("密码：" + password); // 实际生产环境中不要打印密码，此处为调试用

            // 调用Mapper查询：count(*)会返回0（无结果）或1（有结果），不会为null
            int count = this.userMapper.queryUserByLoginId(params);

            // 3. 结果判断：count>0表示账号密码匹配成功
            if (count > 0) {
                System.out.println("==账号密码匹配成功，登录允许==");
                return 1; // 登录成功标识
            } else {
                System.out.println("==账号或密码错误，登录拒绝==");
                return -1; // 登录失败标识
            }
        } catch (Exception e) {
            // 4. 异常处理：捕获所有数据库相关异常，避免服务崩溃
            System.err.println("==查询用户登录信息时发生异常：==");
            e.printStackTrace(); // 打印异常堆栈（生产环境可替换为日志框架记录）
            return -1; // 异常时返回登录失败标识
        }
    }
//    @Override
//   public int queryUserByLoginId(HashMap params) {
//
//        if (params == null ||
//                params.get("loginId") == null || params.get("loginId").toString().trim().isEmpty() ||
//                params.get("password") == null || params.get("password").toString().trim().isEmpty()) {
//            System.out.println("==账号或密码为空，登录失败==");
//            return -1; // 返回-1表示校验失败
//        }
//
//        int num=1;//num=1:查询到数据，账号有权限，num=-1：未查到数据，账号无权限
//        try {
//            System.out.println("==接收到的参数如下==");
//            System.out.println("账号：" + params.get("loginId"));
//            System.out.println("密码：" + params.get("password"));
//            num= this.userMapper.queryUserByLoginId(params);
//            if (num==1) {
//                System.out.println("==查询到数据，账号有权限==");
//
//            } else {
//                System.out.println("==未查到数据，账号无权限==");
//                    num=-1;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            num = -1;
//        }
//        return num;
//    }


    @Override
    public User queryUserInfo(HashMap params){
        User user = null;
      try {
        user= this.userMapper.queryUserInfo(params);
      }catch (Exception e){
          e.printStackTrace();
      }
      return  user;
    }

    @Override
    public int  addUserInfo(User user){
        int num = 0;
        try {
              //先保存账号和密码到tb_sys_user表
            num = this.userMapper.addUser(user);
            if(num==1){//如果num=1则表示保存到tb_sys_user表成功
                //然后保存用户基本信息到tb_sys_user_staff表
               num = this.userMapper.addUserStaff(user);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return num;
    }

    @Override
    public int  updateUserInfo(User user){
        int num = 0;
        try {
            //先调用updateUser方法根据账号修改密码保存到tb_sys_user表
            num = this.userMapper.updateUser(user);
            if(num==1){//如果num=1则表示保存到tb_sys_user表成功
                //然后调用updateUserStaff方法根据账号修改用户基本信息保存到tb_sys_user_staff表
                num = this.userMapper.updateUserStaff(user);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return num;
    }

    @Override
    public int deleteUserInfoByLoginId(HashMap params){
        int num=0;
        try {
            //先调用deleteUserByLoginId方法根据账号删除一条tb_sys_user表中数据
            num = this.userMapper.deleteUserByLoginId(params);
            if(num==1){//如果num=1则表示tb_sys_user表中数据删除成功
                //然后调用deleteUserStaffByLoginId方法根据账号删除一条tb_sys_user_staff表中数据
                num = this.userMapper.deleteUserStaffByLoginId(params);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return num;

    }
}
