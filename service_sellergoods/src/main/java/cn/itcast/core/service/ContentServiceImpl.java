package cn.itcast.core.service;

import cn.itcast.core.dao.ad.ContentDao;
import cn.itcast.core.pojo.ad.Content;
import cn.itcast.core.pojo.ad.ContentQuery;
import cn.itcast.core.pojo.entity.PageResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentDao contentDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<Content> findAll() {
        List<Content> contents = contentDao.selectByExample(null);
        return contents;
    }

    @Override
    public Content findOne(Long id) {
        Content content = contentDao.selectByPrimaryKey(id);
        return content;
    }

    @Override
    public void add(Content content) {
        contentDao.insertSelective(content);
        redisTemplate.boundHashOps("content").delete(content.getCategoryId());
    }

    @Override
    public void delete(Long[] ids) {
        if (ids != null) {
            for (Long id : ids) {
                Content content = contentDao.selectByPrimaryKey(id);
                redisTemplate.boundHashOps("content").delete(content.getCategoryId());
                contentDao.deleteByPrimaryKey(id);
            }
        }
    }

    @Override
    public void update(Content content) {
        Content content1 = contentDao.selectByPrimaryKey(content.getId());
        redisTemplate.boundHashOps("content").delete(content1.getCategoryId());
        contentDao.updateByPrimaryKeySelective(content);
        // 分类id发生变化
        if(content1.getCategoryId().longValue()!=content.getCategoryId().longValue()){
            redisTemplate.boundHashOps("content").delete(content.getCategoryId());
        }

    }

    @Override
    public PageResult search(Content content, Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        ContentQuery contentQuery = new ContentQuery();
        ContentQuery.Criteria criteria = contentQuery.createCriteria();
        if (content.getCategoryId() != null && !"".equals(String.valueOf(content.getCategoryId()))) {
            criteria.andCategoryIdEqualTo(content.getCategoryId());
        }
        Page<Content> contents = (Page<Content>) contentDao.selectByExample(contentQuery);
        return new PageResult(contents.getTotal(), contents.getResult());
    }

    @Override
    public List<Content> findByCategoryId(Long id) {
        // 先查询redis数据库中 是否有缓存的数据
        List<Content> contentList = (List<Content>) redisTemplate.boundHashOps("content").get(id);
        if (contentList == null) {
            // 缓存中读取数据为空，从数据库中读取
            ContentQuery contentQuery = new ContentQuery();
            ContentQuery.Criteria criteria = contentQuery.createCriteria();
            if (id != null) {
                criteria.andCategoryIdEqualTo(id);
                criteria.andStatusEqualTo("1");
            }
            List<Content> contents = contentDao.selectByExample(contentQuery);
            redisTemplate.boundHashOps("content").put(id,contents);
        }
        return contentList;
    }
}
