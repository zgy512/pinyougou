package cn.itcast.core.service;

import cn.itcast.core.dao.specification.SpecificationOptionDao;
import cn.itcast.core.dao.template.TypeTemplateDao;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.specification.SpecificationOption;
import cn.itcast.core.pojo.specification.SpecificationOptionQuery;
import cn.itcast.core.pojo.template.TypeTemplate;
import cn.itcast.core.pojo.template.TypeTemplateQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TemplateServiceImpl implements TemplateService{
    @Autowired
    private TypeTemplateDao typeTemplateDao;

    @Autowired
    private SpecificationOptionDao specificationOptionDao;
    @Override
    public List<TypeTemplate> findAll() {
        return null;
    }

    @Override
    public PageResult search(TypeTemplate typeTemplate, Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        TypeTemplateQuery typeTemplateQuery = new TypeTemplateQuery();
        TypeTemplateQuery.Criteria criteria = typeTemplateQuery.createCriteria();
        if(typeTemplate!=null){
            if(typeTemplate.getName()!=null&&!"".equals(typeTemplate.getName())){
                criteria.andNameEqualTo(typeTemplate.getName());
            }
        }
        Page<TypeTemplate> specifications = (Page<TypeTemplate>)typeTemplateDao.selectByExample(typeTemplateQuery);
        return new PageResult(specifications.getTotal(),specifications.getResult());
    }

    @Override
    public TypeTemplate findOne(Long id) {
        TypeTemplate typeTemplate = typeTemplateDao.selectByPrimaryKey(id);
        return typeTemplate;
    }

    @Override
    public void add(TypeTemplate typeTemplate) {
        typeTemplateDao.insertSelective(typeTemplate);
    }

    @Override
    public void update(TypeTemplate typeTemplate) {
        typeTemplateDao.updateByPrimaryKey(typeTemplate);
    }

    @Override
    public void delete(Long[] ids) {
        if(ids!=null){
            for (Long id: ids) {
                typeTemplateDao.deleteByPrimaryKey(id);
            }
        }
    }

    @Override
    public List<Map> findBySpecList(Long id) {
        TypeTemplate typeTemplate = typeTemplateDao.selectByPrimaryKey(id);
        String specIds = typeTemplate.getSpecIds();
        List<Map> specList = JSON.parseArray(specIds,Map.class);
        if (specList!=null){
            for (Map map:specList) {
                Long specId = Long.parseLong(String.valueOf(map.get("id")));
                SpecificationOptionQuery specificationOptionQuery = new SpecificationOptionQuery();
                SpecificationOptionQuery.Criteria criteria = specificationOptionQuery.createCriteria();
                criteria.andSpecIdEqualTo(specId);
                List<SpecificationOption> specificationOptionList = specificationOptionDao.selectByExample(specificationOptionQuery);
                map.put("options",specificationOptionList);
            }
        }
        return specList;
    }
}
