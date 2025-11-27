package com.mes.service;

import com.mes.domain.Product;
import com.mes.domain.User;

import java.util.HashMap;
import java.util.List;

//业务逻辑层接口类
public interface ProductService {

    //使用List列表封装实体类，返回多条数据
    List<Product> queryProductInfo(HashMap params);

    List<Product> queryFeaturedProducts();

}
