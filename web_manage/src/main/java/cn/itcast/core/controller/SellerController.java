package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.seller.Seller;
import cn.itcast.core.service.SellerService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/seller")
public class SellerController {
    @Reference
    private SellerService sellerService;
    @RequestMapping("/findAll")
    public List<Seller> findAll() {
        List<Seller> sellerList = sellerService.findAll();
        return sellerList;
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody Seller seller, Integer page, Integer rows) {
        PageResult pageResult = sellerService.search(seller, page, rows);
        return pageResult;
    }

    @RequestMapping("/findOne")
    public Seller findOne(String id) {
        Seller seller = sellerService.findOne(id);
        return seller;
    }

    @RequestMapping("/add")
    public Result addSpecification(@RequestBody Seller seller) {
        try {
            sellerService.add(seller);
            return new Result(true, "添加成功");
        } catch (Exception e) {
            return new Result(false, "添加失败");
        }
    }

    @RequestMapping("/update")
    public Result updateSpecification(@RequestBody Seller seller) {
        try {
            sellerService.update(seller);
            return new Result(true, "更新成功");
        } catch (Exception e) {
            return new Result(false, "更新失败");
        }
    }

    @RequestMapping("/delete")
    public Result deleteSpecification(String[] ids) {
        try {
            sellerService.delete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            return new Result(false, "删除失败");
        }
    }

    @RequestMapping("/search")
    public PageResult search(@RequestBody Seller seller, Integer page, Integer rows) {
        PageResult pageResult = sellerService.search(seller, page, rows);
        return pageResult;
    }
    @RequestMapping("updateStatus")
    public Result updateStatus(String[] ids, String status){
        try {
            sellerService.updateStatus(ids,status);
            return new Result(true, "更新状态成功");
        } catch (Exception e) {
            return new Result(false, "更细状态失败");
        }
    }
}
