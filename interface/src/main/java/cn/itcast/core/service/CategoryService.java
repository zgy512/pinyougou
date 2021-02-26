package cn.itcast.core.service;

import cn.itcast.core.pojo.ad.ContentCategory;
import cn.itcast.core.pojo.entity.PageResult;

import java.util.List;

public interface CategoryService {
    List<ContentCategory> findAll();


    ContentCategory findOne(Long id);

    void add(ContentCategory contentCategory);

    void delete(Long[] ids);

    void update(ContentCategory contentCategory);

    PageResult search(ContentCategory contentCategory, Integer page, Integer rows);
}
