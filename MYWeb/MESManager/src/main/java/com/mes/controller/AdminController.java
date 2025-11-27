package com.mes.controller;

import com.mes.common.Result;
import com.mes.domain.Admin;
import com.mes.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;

@Api(tags = "管理员信息管理")
@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @ApiOperation(value = "管理员登录", notes = "根据账号密码验证管理员登录")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "loginId", value = "管理员账号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "登录密码", required = true, paramType = "query", dataType = "String")
    })
    @GetMapping("/query-admin-by-loginId")
    public Result<Admin> adminLogin(@RequestParam String loginId, @RequestParam String password) {
        HashMap<String, String> params = new HashMap<>();
        params.put("loginId", loginId);
        params.put("password", password);

        int num = adminService.queryAdminByLoginId(params);
        if (num == 1) {
            Admin admin = adminService.queryAdminInfo(params);
            return Result.success("登录成功", admin);
        } else {
            return Result.error("管理员账号或密码错误！");
        }
    }
}