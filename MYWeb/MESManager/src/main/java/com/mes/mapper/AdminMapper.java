package com.mes.mapper;

import com.mes.domain.Admin;
import java.util.HashMap;

public interface AdminMapper {
    // 验证管理员账号密码（返回匹配的数量）
    int queryAdminByLoginId(HashMap<String, String> params);

    // 查询管理员信息
    Admin queryAdminInfo(HashMap<String, String> params);
}