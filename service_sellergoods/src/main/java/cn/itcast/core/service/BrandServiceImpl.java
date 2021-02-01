package cn.itcast.core.service;

import cn.itcast.core.dao.good.BrandDao;
import cn.itcast.core.pojo.good.Brand;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandDao brandDao;
    @Override
    public List<Brand> findAll() {
        PageHelper.startPage(1,10);
        List<Brand> brandList = brandDao.selectByExample(null);
        return brandList;
    }

    @Override
    public Brand findOne(Long id) {
        return null;
    }

    @Override
    public void add(Brand brand) {

    }

    @Override
    public void update(Brand brand) {

    }

    @Override
    public void delete(List list) {

    }
}
