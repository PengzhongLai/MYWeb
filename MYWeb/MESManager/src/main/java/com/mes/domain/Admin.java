package com.mes.domain;

import java.util.Date;

public class Admin {
    private String loginId;      // 管理员登录账号（主键）
    private String password;     // 登录密码
    private Date createTime;     // 账号创建时间

    // getter和setter方法
    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}