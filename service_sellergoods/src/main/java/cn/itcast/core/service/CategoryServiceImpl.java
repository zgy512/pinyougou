package cn.itcast.core.service;

import cn.itcast.core.dao.ad.ContentCategoryDao;
import cn.itcast.core.pojo.ad.ContentCategory;
import cn.itcast.core.pojo.ad.ContentCategoryQuery;
import cn.itcast.core.pojo.entity.PageResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private ContentCategoryDao contentCategoryDao;

    @Override
    public List<ContentCategory> findAll() {
        List<ContentCategory> contentCategories = contentCategoryDao.selectByExample(null);
        return contentCategories;
    }

    @Override
    public ContentCategory findOne(Long id) {
        ContentCategory contentCategory = contentCategoryDao.selectByPrimaryKey(id);
        return contentCategory;
    }

    @Override
    public void add(ContentCategory contentCategory) {
        contentCategoryDao.insertSelective(contentCategory);
    }

    @Override
    public void delete(Long[] ids) {
        if(ids!=null){
            for (Long id:
                 ids) {
                contentCategoryDao.deleteByPrimaryKey(id);
            }
        }
    }

    @Override
    public void update(ContentCategory contentCategory) {
        contentCategoryDao.updateByPrimaryKey(contentCategory);
    }

    @Override
    public PageResult search(ContentCategory contentCategory, Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        ContentCategoryQuery contentCategoryQuery = new ContentCategoryQuery();
        ContentCategoryQuery.Criteria criteria = contentCategoryQuery.createCriteria();
        if(contentCategory.getName()!=null && !"".equals(contentCategory.getName())){
            criteria.andNameEqualTo(contentCategory.getName());
        }
        Page<ContentCategory> contentCategories = (Page<ContentCategory>) contentCategoryDao.selectByExample(contentCategoryQuery);
        return new PageResult(contentCategories.getTotal(),contentCategories.getResult());
    }
}
