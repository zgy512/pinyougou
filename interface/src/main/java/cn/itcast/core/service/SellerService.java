package cn.itcast.core.service;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.seller.Seller;

import java.util.List;

public interface SellerService {
    List<Seller> findAll();

    PageResult search(Seller seller, Integer page, Integer rows);

    Seller findOne(String id);

    void add(Seller seller);

    void update(Seller seller);

    void delete(String[] ids);

    void updateStatus(String[] sellerId, String status);
}
