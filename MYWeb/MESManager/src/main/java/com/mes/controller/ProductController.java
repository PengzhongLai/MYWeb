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
import org.springframework.web.multipart.MultipartFile;
import com.mes.service.FileService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Api(tags = "货品管理", description = "用户货品增删改查操作")
@RestController
@CrossOrigin //解决跨域问题
@RequestMapping("/product") //所有接口的父级路径
public class ProductController {

    @Autowired
    private ProductService productService;//自动装配service
    @Autowired // 新增FileService注入
    private FileService fileService; // 声明FileService实例

    @ApiOperation(value = "实现根据销售地和发布状态查询货品数据", notes = "实现根据销售地和发布状态查询货品数据")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "publishState", value = "发布状态", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "placeSale", value = "销售地", required = false, paramType = "query", dataType = "String"),
    })
    @GetMapping("/query-product-info")
    public ResponseEntity<?> queryProductInfo(@RequestParam HashMap params) {
        JSONObject json = new JSONObject();
        List<Product> list = this.productService.queryProductInfo(params);
        if (list != null) {
            json.put("data", list);
            json.put("msg", "共查询到" + list.size() + "记录");//list.size()获取列表的长度
            return ResponseEntity.ok(json);
        } else {
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
        // 新增日志：打印每个商品的ID和好评率
        for (Product p : list) {
            System.out.println("商品ID：" + p.getId() + "，好评率：" + p.getGoodCommentRate());
        }
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

    @ApiOperation(value = "更新商品信息", notes = "根据ID更新商品记录")
    @PostMapping("/update")
    public ResponseEntity<?> updateProduct(@ModelAttribute Product product,
                                           @RequestParam(required = false) MultipartFile imgFile,
                                           HttpServletRequest request) {
        JSONObject json = new JSONObject();
        try {
            // 处理图片更新（如果有新图片）
            if (imgFile != null && !imgFile.isEmpty()) {
                JSONObject uploadResult = fileService.fileUpload(
                        new ArrayList<>(Collections.singletonList(imgFile)), request);
                if (uploadResult.getInteger("code") == 1) {
                    // 从返回的data中获取第一个文件路径（单文件上传场景）
                    List<String> paths = (List<String>) uploadResult.get("data");
                    if (!paths.isEmpty()) {
                        product.setImgSrc(paths.get(0)); // 保存相对路径
                    }
                }
            }
            int rows = productService.updateProduct(product);
            if (rows > 0) {
                json.put("code", 1);
                json.put("msg", "更新成功");
            } else {
                json.put("code", 0);
                json.put("msg", "更新失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", -1);
            json.put("msg", "服务器异常");
        }
        return ResponseEntity.ok(json);
    }

    @ApiOperation(value = "删除商品", notes = "根据ID删除商品记录")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id) {
        JSONObject json = new JSONObject();
        int rows = productService.deleteProductById(id);
        if (rows > 0) {
            json.put("code", 1);
            json.put("msg", "删除成功");
        } else {
            json.put("code", 0);
            json.put("msg", "删除失败");
        }
        return ResponseEntity.ok(json);
    }

    @ApiOperation(value = "新增商品", notes = "插入新商品记录，支持图片上传")
    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@ModelAttribute Product product,
                                        @RequestParam(required = false) MultipartFile imgFile,
                                        @RequestParam(required = false) String useDefaultImg,
                                        HttpServletRequest request) {
        JSONObject json = new JSONObject();
        try {
            // 处理图片（默认图或上传图）
            if ("true".equals(useDefaultImg)) {
                product.setImgSrc("../images/productImg/default.png"); // 默认图片路径
            } else if (imgFile != null && !imgFile.isEmpty()) {
                JSONObject uploadResult = fileService.fileUpload(
                        new ArrayList<>(Collections.singletonList(imgFile)), request);
                if (uploadResult.getInteger("code") == 1) {
                    // 从返回的data中获取第一个文件路径（单文件上传场景）
                    List<String> paths = (List<String>) uploadResult.get("data");
                    if (!paths.isEmpty()) {
                        product.setImgSrc(paths.get(0)); // 保存相对路径
                    }
                }
            }

            int rows = productService.addProduct(product);
            if (rows > 0) {
                json.put("code", 1);
                json.put("msg", "新增成功");
            } else {
                json.put("code", 0);
                json.put("msg", "新增失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", -1);
            json.put("msg", "服务器异常");
        }
        return ResponseEntity.ok(json);
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
