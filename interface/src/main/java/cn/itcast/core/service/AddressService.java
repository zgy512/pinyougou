package cn.itcast.core.service;

import cn.itcast.core.pojo.address.Address;
import cn.itcast.core.pojo.user.User;

import java.util.List;

public interface AddressService {
    List<Address> findListByLoginUser(User userName);
}
