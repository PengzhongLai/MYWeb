package com.mes.controller;

import com.mes.common.Result; // 导入 Result 类
import com.mes.domain.User;
import com.mes.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Api(tags = "用户信息管理", description = "用户信息增删改查操作")
@RestController
@CrossOrigin // 解决跨域问题
@RequestMapping("/user") // 所有接口的父级路径
public class UserController {

    @Autowired
    private UserService userService; // 自动装配service

    @ApiOperation(value = "用户登录", notes = "根据账号密码验证登录")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "loginId", value = "登录账号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "query", dataType = "String"),
    })
    @GetMapping("/query-user-by-loginId")
    public Result<User> login(@RequestParam String loginId, @RequestParam String password) { // 建议使用具体参数，而非 HashMap
        HashMap<String, String> params = new HashMap<>();
        params.put("loginId", loginId);
        params.put("password", password);

        int num = this.userService.queryUserByLoginId(params);
        if (num == 1) {
            // 查询用户的基本信息
            User user = this.userService.queryUserInfo(params);
            // 登录成功，返回状态码1和用户信息
            return Result.success("登录成功", user);
        } else {
            // 登录失败，返回状态码0和错误信息。HTTP状态码依然是200
            return Result.error("账号或密码错误，登录失败！");
        }
    }

    @ApiOperation(value = "查询用户的基本信息", notes = "根据账号查询用户基本信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "loginId", value = "登录账号", required = true, paramType = "query", dataType = "String"),
    })
    @GetMapping("/query-user-info")
    public Result<User> queryUserInfo(@RequestParam String loginId) { // 建议使用具体参数
        HashMap<String, String> params = new HashMap<>();
        params.put("loginId", loginId);

        User user = this.userService.queryUserInfo(params);
        if (user != null) {
            // 查询成功
            return Result.success("查询成功", user);
        } else {
            // 查询失败（未找到用户），返回错误信息
            return Result.error("未查询到该用户信息！");
        }
    }

    @ApiOperation(value = "新增注册用户", notes = "新增一条用户信息")
    @PostMapping("/add-user-info")
    @ApiImplicitParam(name = "user", value = "新增数据字段的集合", required = true, paramType = "body", dataType = "User")
    public Result<?> addUserInfo(@RequestBody User user) { // 使用 Result<?> 因为成功时可能不需要返回具体数据
        int num = this.userService.addUserInfo(user);
        if (num == 1) {
            return Result.success("新增注册成功！");
        } else {
            return Result.error("新增注册失败！");
        }
    }

    @ApiOperation(value = "修改用户", notes = "根据账号修改一条用户信息")
    @PutMapping("/update-user-info")
    @ApiImplicitParam(name = "user", value = "修改数据字段的集合", required = true, paramType = "body", dataType = "User")
    public Result<?> updateUserInfo(@RequestBody User user) {
        int num = this.userService.updateUserInfo(user);
        if (num == 1) {
            return Result.success("修改成功！");
        } else {
            return Result.error("修改失败！");
        }
    }

    @ApiOperation(value = "删除用户", notes = "根据账号删除一条用户信息")
    @DeleteMapping("/delete-user-info-by-loginId")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "loginId", value = "登录账号", required = true, paramType = "query", dataType = "String"),
    })
    public Result<?> deleteUserInfoByLoginId(@RequestParam String loginId) { // 建议使用具体参数
        HashMap<String, String> params = new HashMap<>();
        params.put("loginId", loginId);

        int num = this.userService.deleteUserInfoByLoginId(params);
        if (num == 1) {
            return Result.success("删除成功！");
        } else {
            return Result.error("删除失败！");
        }
    }
}