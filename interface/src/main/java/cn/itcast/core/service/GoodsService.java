package cn.itcast.core.service;

import cn.itcast.core.pojo.entity.GoodsEntity;
import cn.itcast.core.pojo.entity.PageResult;

import java.util.List;

public interface GoodsService {
    PageResult search(GoodsEntity goodsEntity, Integer page, Integer rows);

    void delete(Long[] ids);

    void add(GoodsEntity goodsEntity);

    void update(GoodsEntity goodsEntity);

    GoodsEntity findOne(Long id);

    List<GoodsEntity> findAll();

    void updateStatus(Long[] ids, String status);
}
