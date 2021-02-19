package cn.itcast.core.service;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.SpecEntity;
import cn.itcast.core.pojo.specification.Specification;

import java.util.List;
import java.util.Map;

/**
 * 规格接口
 */
public interface SpecifitionService {

    List<Specification> findAll();

    PageResult search(Specification specification, Integer page, Integer rows);

    SpecEntity findOne(Long id);

    void add(SpecEntity specEntity);

    void update(SpecEntity specEntity);

    void delete(Long[] ids);

    List<Map> selectOptionList();
}
