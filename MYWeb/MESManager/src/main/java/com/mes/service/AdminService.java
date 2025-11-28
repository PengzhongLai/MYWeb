package com.mes.service;

import com.mes.domain.Admin;
import java.util.HashMap;

public interface AdminService {
    // 验证管理员账号密码（返回1表示验证通过，-1表示失败）
    int queryAdminByLoginId(HashMap<String, String> params);

    // 根据账号查询管理员信息
    Admin queryAdminInfo(HashMap<String, String> params);
}