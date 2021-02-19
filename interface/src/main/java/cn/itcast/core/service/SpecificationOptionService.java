package cn.itcast.core.service;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.specification.SpecificationOption;

import java.util.List;

public interface SpecificationOptionService {

    List<SpecificationOption> findAll();

    SpecificationOption findOne(Long id);

    PageResult search(SpecificationOption specificationOption, Integer page, Integer rows);

    void add(SpecificationOption specificationOption);

    void update(SpecificationOption specificationOption);

    void delete(Long[] ids);
}
