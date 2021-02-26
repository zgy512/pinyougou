package cn.itcast.core.service;

import cn.itcast.core.pojo.ad.Content;
import cn.itcast.core.pojo.entity.PageResult;

import java.util.List;

public interface ContentService {
    List<Content> findAll();

    Content findOne(Long id);

    void add(Content content);

    void delete(Long[] ids);

    void update(Content content);

    PageResult search(Content content, Integer page, Integer rows);

    List<Content> findByCategoryId(Long id);
}
