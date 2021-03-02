package cn.itcast.core;

import cn.itcast.core.pojo.address.Address;
import cn.itcast.core.pojo.user.User;
import cn.itcast.core.service.AddressService;

import java.util.List;

public class AddressServiceImpl implements AddressService {
    @Override
    public List<Address> findListByLoginUser(User userName) {
        return null;
    }
}
