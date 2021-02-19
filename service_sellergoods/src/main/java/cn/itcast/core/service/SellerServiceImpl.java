package cn.itcast.core.service;

import cn.itcast.core.dao.seller.SellerDao;
import cn.itcast.core.pojo.entity.AuditStatus;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.seller.Seller;
import cn.itcast.core.pojo.seller.SellerQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SellerServiceImpl implements SellerService{

    @Autowired
    private SellerDao sellerDao;
    @Override
    public List<Seller> findAll() {
        List<Seller> sellerList = sellerDao.selectByExample(null);
        return sellerList;
    }

    @Override
    public PageResult search(Seller seller, Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        SellerQuery sellerQuery = new SellerQuery();
        SellerQuery.Criteria criteria = sellerQuery.createCriteria();
        if(seller.getSellerId()!=null&&!"".equals(seller.getSellerId())){
            criteria.andSellerIdEqualTo(seller.getSellerId());
        }
        Page<Seller> sellers = (Page<Seller>) sellerDao.selectByExample(sellerQuery);
        return new PageResult(sellers.getTotal(),sellers.getResult());
    }

    @Override
    public Seller findOne(String id) {
        Seller seller = sellerDao.selectByPrimaryKey(id);
        return seller;
    }

    @Override
    public void add(Seller seller) {
        // 添加商家 默认未审核状态 并添加入住时间
        seller.setStatus(AuditStatus.NotAudit);
        seller.setCreateTime(new Date());
        sellerDao.insertSelective(seller);
    }

    @Override
    public void update(Seller seller) {
        sellerDao.updateByPrimaryKeySelective(seller);
    }

    @Override
    public void delete(String[] ids) {
        if(ids!=null){
            for(String id : ids){
                sellerDao.deleteByPrimaryKey(id);
            }
        }
    }

    @Override
    public void updateStatus(String[] ids, String status) {
        if(ids!=null){
            for (String sellerId:ids) {
                Seller seller = new Seller();
                seller.setStatus(status);
                seller.setSellerId(sellerId);
                sellerDao.updateByPrimaryKeySelective(seller);
            }
        }
    }
}
