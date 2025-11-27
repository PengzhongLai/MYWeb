package com.mes.controller;

import com.alibaba.fastjson.JSONObject;
import com.mes.domain.Product;
import com.mes.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Api(tags = "货品管理",description = "用户货品增删改查操作")
@RestController
@CrossOrigin //解决跨域问题
@RequestMapping("/product") //所有接口的父级路径
public class ProductController {

    @Autowired
    private ProductService productService;//自动装配service

    @ApiOperation(value = "实现根据销售地和发布状态查询货品数据",notes = "实现根据销售地和发布状态查询货品数据")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name="publishState",value = "发布状态",required = false,paramType = "query",dataType = "String"),
            @ApiImplicitParam(name="placeSale",value = "销售地",required = false,paramType = "query",dataType = "String"),
    })
    @GetMapping("/query-product-info")
    public  ResponseEntity<?> queryProductInfo(@RequestParam HashMap params){
        JSONObject json = new JSONObject();
        List<Product> list=this.productService.queryProductInfo(params);
        if(list !=null){
            json.put("data", list);
            json.put("msg", "共查询到"+list.size()+"记录");//list.size()获取列表的长度
            return ResponseEntity.ok(json);
        }else {
            json.put("data", null);
            json.put("msg", "查询失败！");
            return ResponseEntity.status(500).body(json);
        }

    }

    @ApiOperation(value = "查询好评率≥90的精选商品", notes = "用于精选好品区域展示")
    @GetMapping("/query-featured-products")
    public ResponseEntity<?> queryFeaturedProducts() {
        JSONObject json = new JSONObject();
        List<Product> list = this.productService.queryFeaturedProducts();
        if (list != null) {
            json.put("data", list);
            json.put("msg", "共查询到" + list.size() + "条精选商品");
            return ResponseEntity.ok(json);
        } else {
            json.put("data", null);
            json.put("msg", "查询失败！");
            return ResponseEntity.status(500).body(json);
        }
    }


   /* @ApiOperation(value = "根据主键id查询详情",notes = "根据主键id查询详情")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name="id",value = "主键",required = true,paramType = "query",dataType = "String"),
    })
    @GetMapping("/query-product-info")
    public  ResponseEntity<?> queryProductInfo(@RequestParam HashMap params){
        JSONObject json = new JSONObject();
        List<Product> list=this.productService.queryProductInfo(params);
        if(list !=null){
            json.put("data", list);
            json.put("msg", "共查询到"+list.size()+"记录");//list.size()获取列表的长度
            return ResponseEntity.ok(json);
        }else {
            json.put("data", null);
            json.put("msg", "查询失败！");
            return ResponseEntity.status(500).body(json);
        }

    }*/



}
