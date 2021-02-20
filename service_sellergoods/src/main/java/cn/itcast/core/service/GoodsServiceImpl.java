package cn.itcast.core.service;

import cn.itcast.core.dao.good.GoodsDao;
import cn.itcast.core.dao.good.GoodsDescDao;
import cn.itcast.core.pojo.entity.AuditStatus;
import cn.itcast.core.pojo.entity.GoodsEntity;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.good.Goods;
import cn.itcast.core.pojo.good.GoodsDesc;
import cn.itcast.core.pojo.item.Item;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GoodsServiceImpl implements GoodsService{

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private GoodsDescDao goodsDescDao;

    @Override
    public PageResult search(GoodsEntity goodsEntity, Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        if(goodsEntity.getGoods()!=null){

        }
        if (goodsEntity.getGoodsDesc()!=null){

        }
        if (goodsEntity.getItemList()!=null){

        }
        // 查询 goods
        // 查询 goodsDesc
        // 查询 itemCatList
        return null;
    }

    @Override
    public void delete(Long[] ids) {

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

    private void insertItem(GoodsEntity goodsEntity) {
        
    }

    @Override
    public void update(GoodsEntity goodsEntity) {

    }

    @Override
    public GoodsEntity findOne(Long id) {

        return null;
    }

    @Override
    public List<GoodsEntity> findAll() {
        return null;
    }
}
