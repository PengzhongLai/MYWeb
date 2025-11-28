package com.mes.controller;

import com.alibaba.fastjson.JSONObject;
import com.mes.domain.Admin;
import com.mes.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Api(tags = "管理员管理", description = "管理员登录相关操作")
@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @ApiOperation(value = "管理员登录", notes = "验证账号密码并返回管理员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginId", value = "管理员账号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "管理员密码", required = true, paramType = "query", dataType = "String")
    })
    @GetMapping("/query-admin-by-loginId")
    public ResponseEntity<?> adminLogin(@RequestParam String loginId, @RequestParam String password) {
        JSONObject json = new JSONObject();
        HashMap<String, String> params = new HashMap<>();
        params.put("loginId", loginId);
        params.put("password", password);

        // 调用Service验证账号密码
        int result = adminService.queryAdminByLoginId(params);
        if (result == 1) {
            // 验证通过，返回管理员信息
            Admin admin = adminService.queryAdminInfo(params);
            json.put("code", 1);
            json.put("msg", "登录成功");
            json.put("data", admin);
            return ResponseEntity.ok(json);
        } else {
            json.put("code", -1);
            json.put("msg", "账号或密码错误");
            return ResponseEntity.ok(json);
        }
    }
}