package cn.itcast.core.service;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.good.Brand;

import java.util.List;
import java.util.Map;

/**
 * 商品接口
 */

public interface BrandService {
    List<Brand> findAll();

    Brand findOne(Long id);

    List<Brand> findByPage(Integer page, Integer rows);

    void add(Brand brand);

    void update(Brand brand);

    void delete(Long[] ids);

    List<Map> selectOptionList();

    PageResult search(Brand brand, Integer page, Integer rows);
}
