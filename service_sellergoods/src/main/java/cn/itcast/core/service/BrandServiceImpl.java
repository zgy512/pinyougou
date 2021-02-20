package cn.itcast.core.service;

import cn.itcast.core.dao.good.BrandDao;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.good.BrandQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandDao brandDao;

    @Override
    public List<Brand> findAll() {
//        PageHelper.startPage(1,10);
        List<Brand> brandList = brandDao.selectByExample(null);
        return brandList;
    }

    @Override
    public Brand findOne(Long id) {
        Brand brand = brandDao.selectByPrimaryKey(id);
        return brand;
    }

    @Override
    public List<Brand> findByPage(Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        List<Brand> brandList = brandDao.selectByExample(null);
        return brandList;
    }

    @Override
    public void add(Brand brand) {
        brandDao.insertSelective(brand);
    }

    @Override
    public void update(Brand brand) {
        brandDao.updateByPrimaryKeySelective(brand);
    }

    @Override
    public void delete(Long[] ids) {
        if(ids!=null){
            for(Long id:ids){
                brandDao.deleteByPrimaryKey(id);
            }
        }
    }

    @Override
    public List<Map> selectOptionList() {
        List<Map> maps = brandDao.selectOptionList();
        return maps;
    }

    @Override
    public PageResult search(Brand brand, Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        BrandQuery brandQuery = new BrandQuery();
        BrandQuery.Criteria criteria = brandQuery.createCriteria();
        if (brand != null) {
            if (brand.getFirstChar() != null && !"".equals(brand.getFirstChar())) {
                criteria.andFirstCharEqualTo(brand.getFirstChar());
            } else if (brand.getName() != null && !"".equals(brand.getName())) {
                criteria.andNameEqualTo(brand.getName());
            }
        }
        Page<Brand> brandList = (Page<Brand>)brandDao.selectByExample(brandQuery);
        for (Brand brand1:brandList.getResult()) {
            System.out.println(brand1.getName()+":"+brand1.getFirstChar());
        }
        return new PageResult(brandList.getTotal(),brandList.getResult());
    }
}
