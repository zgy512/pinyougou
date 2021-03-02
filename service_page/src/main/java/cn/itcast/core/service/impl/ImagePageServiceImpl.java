package cn.itcast.core.service.impl;

import cn.itcast.core.dao.good.GoodsDao;
import cn.itcast.core.dao.good.GoodsDescDao;
import cn.itcast.core.dao.item.ItemCatDao;
import cn.itcast.core.dao.item.ItemDao;
import cn.itcast.core.pojo.good.Goods;
import cn.itcast.core.pojo.good.GoodsDesc;
import cn.itcast.core.pojo.item.Item;
import cn.itcast.core.pojo.item.ItemCat;
import cn.itcast.core.pojo.item.ItemQuery;
import cn.itcast.core.service.ImagePageService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImagePageServiceImpl implements ImagePageService, ServletContextAware {

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private GoodsDescDao goodsDescDao;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private ItemCatDao itemCatDao;

    private ServletContext servletContext;

    @Override
    public boolean genItemHtml(Long goodsid) {
        try {
            // 获取 Configuration
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            Template template = configuration.getTemplate("item.ftl");
            Map modelMap = new HashMap();
            String path = goodsid+".html";
            String realPath = getRealPath(path);
            // 获取商品表数据
            Goods goods = goodsDao.selectByPrimaryKey(goodsid);
            modelMap.put("goods",goods);
            // 获取商品表扩展数据
            GoodsDesc goodsDesc = goodsDescDao.selectByPrimaryKey(goodsid);
            modelMap.put("goodsDesc",goodsDesc);
            // 获取获取商品三级分类
            ItemCat itemCat1 = itemCatDao.selectByPrimaryKey(goods.getCategory1Id());
            ItemCat itemCat2 = itemCatDao.selectByPrimaryKey(goods.getCategory2Id());
            ItemCat itemCat3 = itemCatDao.selectByPrimaryKey(goods.getCategory3Id());
            modelMap.put("itemCat1",itemCat1);
            modelMap.put("itemCat2",itemCat2);
            modelMap.put("itemCat3",itemCat3);
            // 获取商品sku
            ItemQuery itemQuery = new ItemQuery();
            ItemQuery.Criteria criteria = itemQuery.createCriteria();
            criteria.andGoodsIdEqualTo(goodsid);
            itemQuery.setOrderByClause("is_default desc");
            List<Item> itemList = itemDao.selectByExample(itemQuery);
            modelMap.put("itemList",itemList);
            FileWriter out = new FileWriter(new File(realPath));
            template.process(modelMap,out);
            out.close();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    private String getRealPath(String path) {
        return servletContext.getRealPath(path);
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
