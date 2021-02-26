package cn.itcast.core.service;

import cn.itcast.core.dao.good.BrandDao;
import cn.itcast.core.dao.good.GoodsDao;
import cn.itcast.core.dao.good.GoodsDescDao;
import cn.itcast.core.dao.item.ItemDao;
import cn.itcast.core.dao.seller.SellerDao;
import cn.itcast.core.pojo.entity.AuditStatus;
import cn.itcast.core.pojo.entity.GoodsEntity;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.good.Goods;
import cn.itcast.core.pojo.good.GoodsDesc;
import cn.itcast.core.pojo.good.GoodsQuery;
import cn.itcast.core.pojo.item.Item;
import cn.itcast.core.pojo.item.ItemQuery;
import cn.itcast.core.pojo.seller.Seller;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class GoodsServiceImpl implements GoodsService{

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private GoodsDescDao goodsDescDao;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private BrandDao brandDao;

    @Autowired
    private SellerDao sellerDao;

    @Override
    public PageResult search(GoodsEntity goodsEntity, Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        GoodsQuery goodsQuery = new GoodsQuery();
        if(goodsEntity.getGoods()!=null){
            GoodsQuery.Criteria criteria = goodsQuery.createCriteria();
            if(goodsEntity.getGoods().getAuditStatus()!=null){
                criteria.andAuditStatusEqualTo(goodsEntity.getGoods().getAuditStatus());
            }
        }
        Page<Goods> goodsPage = (Page<Goods>)goodsDao.selectByExample(goodsQuery);
        return new PageResult(goodsPage.getTotal(),goodsPage.getResult());
    }

    @Override
    public void delete(Long[] ids) {
        if(ids!=null){
            // good 删除 不删数据  把 isDelete 状态改为1 即逻辑删除
            for (Long id:ids) {
                Goods goods = new Goods();
                goods.setId(id);
                goods.setIsDelete("1");
                goodsDao.updateByPrimaryKeySelective(goods);
            }
        }
    }

    @Override
    public void add(GoodsEntity goodsEntity) {
        // 保存商品对象
        Goods goods = goodsEntity.getGoods();
        goods.setAuditStatus(AuditStatus.NotAudit);
        goodsDao.insertSelective(goods);
        // 保存商品扩展
        GoodsDesc goodsDesc = goodsEntity.getGoodsDesc();
        goodsDesc.setGoodsId(goods.getId());
        goodsDescDao.insertSelective(goodsDesc);
        // 保存商品SKU
        insertItem(goodsEntity);
    }

    /**
     * 保存库存数据
     * @param goodsEntity
     */
    private void insertItem(GoodsEntity goodsEntity) {
        if("1".equals(goodsEntity.getGoods().getIsEnableSpec())){
            if(goodsEntity.getItemList()!=null){
                for (Item item:goodsEntity.getItemList()) {
                    String title = goodsEntity.getGoods().getGoodsName();
                    String specJsonStr = item.getSpec();
                    Map specMap = JSON.parseObject(specJsonStr,Map.class);
                    Collection<String> specValues = specMap.values();
                    for (String spec:specValues) {
                        title +=" " +spec;
                    }
                    item.setTitle(title);
                    setItemValuse(goodsEntity,item);
                    itemDao.insertSelective(item);
                }
            }
        }else {
            Item item = new Item();
            item.setPrice(new BigDecimal("99999999"));
            item.setTitle(goodsEntity.getGoods().getGoodsName());
            item.setNum(0);
            item.setSpec("{}");
            setItemValuse(goodsEntity,item);
            itemDao.insertSelective(item);
        }
    }

    /**
     * 设置item 属性值
     * @param goodsEntity
     * @param item
     */
    private void setItemValuse(GoodsEntity goodsEntity, Item item) {
        // good id
        item.setGoodsId(goodsEntity.getGoods().getId());
        // 创建时间
        item.setCreateTime(new Date());
        // 更新时间
        item.setUpdateTime(new Date());
        // 未审核状态 0
        item.setStatus(AuditStatus.NotAudit);
        // 分类 id
        item.setCategoryid(goodsEntity.getGoods().getCategory3Id());
        // 分类名称
        // 品牌名称
        Brand brand = brandDao.selectByPrimaryKey(goodsEntity.getGoods().getBrandId());
        item.setBrand(brand.getName());
        // 商家名称
        Seller seller = sellerDao.selectByPrimaryKey(goodsEntity.getGoods().getSellerId());
        item.setSeller(seller.getName());
        // 图片
        String itemImages = goodsEntity.getGoodsDesc().getItemImages();
        List<Map> mapList = JSON.parseArray(itemImages,Map.class);
        if(mapList!=null&&mapList.size()>0){
            String image = String.valueOf(mapList.get(0).get("url"));
            item.setImage(image);
        }
    }

    @Override
    public void update(GoodsEntity goodsEntity) {
        // goods 更新
        goodsDao.updateByPrimaryKeySelective(goodsEntity.getGoods());
        // goodsDesc 更新
        goodsDescDao.updateByPrimaryKeySelective(goodsEntity.getGoodsDesc());
        // 库存 更新
        ItemQuery itemQuery = new ItemQuery();
        ItemQuery.Criteria criteria = itemQuery.createCriteria();
        criteria.andGoodsIdEqualTo(goodsEntity.getGoods().getId());
        itemDao.deleteByExample(itemQuery);
        insertItem(goodsEntity);
    }

    @Override
    public GoodsEntity findOne(Long id) {
        // goods
        Goods goods = goodsDao.selectByPrimaryKey(id);
        // goodsDesc
        GoodsDesc goodsDesc = goodsDescDao.selectByPrimaryKey(id);
        // item
        ItemQuery itemQuery = new ItemQuery();
        ItemQuery.Criteria criteria = itemQuery.createCriteria();
        criteria.andGoodsIdEqualTo(id);
        List<Item> items = itemDao.selectByExample(itemQuery);
        GoodsEntity goodsEntity = new GoodsEntity();
        goodsEntity.setGoods(goods);
        goodsEntity.setGoodsDesc(goodsDesc);
        goodsEntity.setItemList(items);
        return goodsEntity;
    }

    @Override
    public List<GoodsEntity> findAll() {
        return null;
    }

    @Override
    public void updateStatus(Long[] ids, String status) {
        if(ids !=null){
            for (Long id: ids) {
                Goods goods = goodsDao.selectByPrimaryKey(id);
                if (!status.equals(goods.getAuditStatus())){
                    goods.setAuditStatus(status);
                }
                goodsDao.updateByPrimaryKeySelective(goods);
            }
        }
    }
}
