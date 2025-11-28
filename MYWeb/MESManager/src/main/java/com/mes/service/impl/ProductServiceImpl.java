package com.mes.service.impl;

import com.mes.domain.Product;
import com.mes.domain.User;
import com.mes.mapper.ProductMapper;
import com.mes.mapper.UserMapper;
import com.mes.service.ProductService;
import com.mes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    @Override
    public int updateProduct(Product product) {
        try {
            // 设置更新时间为当前时间
            product.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            return productMapper.updateProduct(product);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int deleteProductById(int id) {
        try {
            return productMapper.deleteProductById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    //
    @Override
    public int addProduct(Product product) {
        try {
            // 设置默认时间
            String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            product.setAddTime(now);
            product.setUpdateTime(now);
            // 未发布状态默认0
            if (product.getPublishState() == 0) {
                product.setPublishTime(null);
            } else {
                product.setPublishTime(now); // 已发布则设置发布时间
            }
            return productMapper.addProduct(product);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
