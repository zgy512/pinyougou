package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.GoodsEntity;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.service.GoodsService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("goods")
public class GoodsController {

    @Reference
    private GoodsService goodsService;
    @RequestMapping("findAll")
    public List<GoodsEntity> findAll(){
        List<GoodsEntity> goodsEntityList = goodsService.findAll();
        return goodsEntityList;
    }

    @RequestMapping("findOne")
    public GoodsEntity findOne(Long id){
        GoodsEntity goodsEntity = goodsService.findOne(id);
        return goodsEntity;
    }
    @RequestMapping("add")
    public Result add(@RequestBody GoodsEntity goodsEntity){
        try {
            goodsService.add(goodsEntity);
            return new Result(false,"添加成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"添加失败");
        }
    }
    @RequestMapping("update")
    public Result update(@RequestBody GoodsEntity goodsEntity){
        try {
            goodsService.update(goodsEntity);
            return new Result(false,"更新成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"更新失败");
        }
    }
    @RequestMapping("delete")
    public Result delete(Long[] ids){
        try {
            goodsService.delete(ids);
            return new Result(false,"删除成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"删除失败");
        }
    }
    @RequestMapping("search")
    public PageResult search(GoodsEntity goodsEntity,Integer page, Integer rows){
        try {
            PageResult search = goodsService.search(goodsEntity, page, rows);
            return search;
        }catch (Exception e){
            e.printStackTrace();
            return new PageResult("fail",0L,null);
        }
    }
}
