package cn.itcast.core.service;

import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.good.Brand;

import java.util.List;

/**
 * 商品接口
 */

public interface BrandService {
    List<Brand> findAll();
    Brand findOne(Long id);
    void add(Brand brand);
    void update(Brand brand);
    void delete(List list);
}
