package cn.itcast.core.service;

import cn.itcast.core.dao.specification.SpecificationDao;
import cn.itcast.core.dao.specification.SpecificationOptionDao;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.SpecEntity;
import cn.itcast.core.pojo.specification.Specification;
import cn.itcast.core.pojo.specification.SpecificationOption;
import cn.itcast.core.pojo.specification.SpecificationOptionQuery;
import cn.itcast.core.pojo.specification.SpecificationQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SpecifitionServiceImpl implements SpecifitionService{
    @Autowired
    private SpecificationDao specificationDao;
    @Autowired
    private SpecificationOptionDao specificationOptionDao;
    @Override
    public List<Specification> findAll() {
        return specificationDao.selectByExample(null);
    }

    @Override
    public PageResult search(Specification specification, Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        SpecificationQuery specificationQuery = new SpecificationQuery();
        SpecificationQuery.Criteria criteria = specificationQuery.createCriteria();
        if(specification!=null){
            if(specification.getSpecName()!=null&&!"".equals(specification.getSpecName())){
                criteria.andSpecNameLike(specification.getSpecName());
            }
        }
        Page<Specification> specifications = (Page<Specification>)specificationDao.selectByExample(specificationQuery);
        return new PageResult(specifications.getTotal(),specifications.getResult());
    }

    @Override
    public SpecEntity findOne(Long id) {
        Specification specification = specificationDao.selectByPrimaryKey(id);
        SpecificationOptionQuery specificationOptionQuery = new SpecificationOptionQuery();
        SpecificationOptionQuery.Criteria criteria = specificationOptionQuery.createCriteria();
        criteria.andIdEqualTo(id);
        List<SpecificationOption> specificationOptionList = specificationOptionDao.selectByExample(specificationOptionQuery);
        SpecEntity specEntity = new SpecEntity();
        specEntity.setSpecification(specification);
        specEntity.setSpecificationOptionList(specificationOptionList);
        return specEntity;
    }

    @Override
    public void add(SpecEntity specEntity) {
        // 插入
        specificationDao.insertSelective(specEntity.getSpecification());
        // 获取specId
        SpecificationQuery query = new SpecificationQuery();
        SpecificationQuery.Criteria specCriteria = query.createCriteria();
        specCriteria.andSpecNameLike(specEntity.getSpecification().getSpecName());
        List<Specification> specificationList = specificationDao.selectByExample(query);
        if (specificationList!=null){
            for (Specification specification:specificationList) {
                if(specEntity.getSpecification().getSpecName().equals(specification.getSpecName())){
                    specEntity.setSpecification(specification);
                }
            }
        }
        if (specEntity.getSpecificationOptionList()!=null){
            for(SpecificationOption specificationOption:specEntity.getSpecificationOptionList()){
                specificationOption.setSpecId(specEntity.getSpecification().getId());
                specificationOptionDao.insertSelective(specificationOption);
            }
        }
    }

    @Override
    public void update(SpecEntity specEntity) {
        // 更新规格
        specificationDao.updateByPrimaryKeySelective(specEntity.getSpecification());
        // 删除规格选项
        SpecificationOptionQuery specificationOptionQuery = new SpecificationOptionQuery();
        SpecificationOptionQuery.Criteria criteria = specificationOptionQuery.createCriteria();
        criteria.andSpecIdEqualTo(specEntity.getSpecification().getId());
        specificationOptionDao.deleteByExample(specificationOptionQuery);
        // 添加规格选项
        if (specEntity.getSpecificationOptionList()!=null){
            for(SpecificationOption specificationOption:specEntity.getSpecificationOptionList()){
                specificationOption.setSpecId(specEntity.getSpecification().getId());
                specificationOptionDao.insertSelective(specificationOption);
            }
        }
    }

    @Override
    public void delete(Long[] ids) {
        if(ids!=null){
            for(Long id:ids){
                // 删除规格对象
                specificationDao.deleteByPrimaryKey(id);
                // 删除规格选项集合
                SpecificationOptionQuery specificationOptionQuery = new SpecificationOptionQuery();
                SpecificationOptionQuery.Criteria criteria = specificationOptionQuery.createCriteria();
                criteria.andSpecIdEqualTo(id);
                specificationOptionDao.deleteByExample(specificationOptionQuery);
            }
        }
    }

    @Override
    public List<Map> selectOptionList() {
        return specificationDao.selectOptionList();
    }
}
