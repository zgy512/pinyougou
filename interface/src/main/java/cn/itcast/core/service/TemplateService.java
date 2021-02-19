package cn.itcast.core.service;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.template.TypeTemplate;

import java.util.List;

public interface TemplateService {
    List<TypeTemplate> findAll();

    PageResult search(TypeTemplate typeTemplate, Integer page, Integer rows);

    TypeTemplate findOne(Long id);

    void add(TypeTemplate typeTemplate);

    void update(TypeTemplate typeTemplate);

    void delete(Long[] ids);
}
