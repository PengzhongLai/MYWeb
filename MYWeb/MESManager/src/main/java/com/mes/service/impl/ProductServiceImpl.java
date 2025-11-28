package com.mes.service.impl;

import com.mes.domain.Product;
import com.mes.domain.User;
import com.mes.mapper.ProductMapper;
import com.mes.mapper.UserMapper;
import com.mes.service.ProductService;
import com.mes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//业务逻辑层接口实现类
@Service //@Service注解是Spring框架中用于标注业务逻辑层的实现类
public class ProductServiceImpl implements ProductService {
 @Autowired
 private ProductMapper productMapper;

   @Override
    public List<Product> queryProductInfo(HashMap params){
       List<Product> list=new ArrayList<>();//new一个数组对象分配内存空间
       try {
           if(params.get("placeSale") != null){
               String placeSale=params.get("placeSale").toString();
               //split函数用来将字符串以逗号分割转化为数组
               String[] placeSaleList=placeSale.split(",");
               params.put("placeSaleList",placeSaleList);
           }
           list=this.productMapper.queryProductInfo(params);
       }catch (Exception e){
           e.printStackTrace();
       }
       return list;
    }

    @Override
    public List<Product> queryFeaturedProducts() {
        List<Product> list = new ArrayList<>();
        try {
            list = this.productMapper.queryFeaturedProducts();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
