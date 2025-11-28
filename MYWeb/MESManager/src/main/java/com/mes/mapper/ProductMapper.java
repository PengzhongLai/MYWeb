package com.mes.mapper;

import com.mes.domain.Product;
import com.mes.domain.User;

import java.util.HashMap;
import java.util.List;

public interface ProductMapper {

    //使用List列表封装实体类，返回多条数据
    List<Product> queryProductInfo(HashMap params);

    List<Product> queryFeaturedProducts();

    int updateProduct(Product product);

    int deleteProductById(int id);

    int addProduct(Product product);
}
