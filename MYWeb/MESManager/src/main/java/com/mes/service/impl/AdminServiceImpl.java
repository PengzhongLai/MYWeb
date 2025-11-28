package com.mes.service.impl;

import com.mes.domain.Admin;
import com.mes.mapper.AdminMapper;
import com.mes.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public int queryAdminByLoginId(HashMap<String, String> params) {
        // 参数校验
        if (params == null) {
            System.out.println("==管理员登录参数为空，登录失败==");
            return -1;
        }
        String loginId = (params.get("loginId") != null) ? params.get("loginId").toString().trim() : "";
        String password = (params.get("password") != null) ? params.get("password").toString().trim() : "";

        if (loginId.isEmpty() || password.isEmpty()) {
            System.out.println("==管理员账号或密码为空，登录失败==");
            return -1;
        }

        // 数据库查询
        try {
            System.out.println("==管理员登录参数：账号=" + loginId + "，密码=" + password);
            int count = adminMapper.queryAdminByLoginId(params);
            return count > 0 ? 1 : -1; // 1表示验证通过，-1表示失败
        } catch (Exception e) {
            System.err.println("==管理员登录查询异常：==");
            e.printStackTrace();
            return -1;
        }
    }

//    @Override
//    public Admin queryAdminInfo(HashMap<String, String> params) {
//        try {
//            return adminMapper.queryAdminInfo(params);
//        } catch (Exception e) {
//            System.err.println("==查询管理员信息异常：==");
//            e.printStackTrace();
//            return null;
//        }
//    }

    @Override
    public Admin queryAdminInfo(HashMap<String, String> params) {
        // 补充参数校验
        if (params == null || params.get("loginId") == null || params.get("password") == null) {
            System.out.println("==查询参数不完整==");
            return null;
        }
        try {
            return adminMapper.queryAdminInfo(params);
        } catch (Exception e) {
            System.err.println("==查询管理员信息异常：==");
            e.printStackTrace();
            return null;
        }
    }
}