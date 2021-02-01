package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.good.Brand;
import com.alibaba.dubbo.config.annotation.Reference;
import cn.itcast.core.service.BrandService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品控制器
 */


@RestController
@RequestMapping("/brand")
public class BrandController {
    @Reference
    private BrandService brandService;
    @RequestMapping("/findAll")
    public List<Brand> findAll(){
        List<Brand> brandList = brandService.findAll();
        return brandList;
    }

    @RequestMapping("/findOne")
    public Brand findOne(Long id){
        Brand one = brandService.findOne(id);
        return one;
    }

    @RequestMapping("/add")
    public Result addBrand(@RequestBody Brand brand){
        //商品添加
        try {
            brandService.add(brand);
            return new Result(true,"ok");
        }catch (Exception e){
            return new Result(false,"fail");
        }
    }
    @RequestMapping("/delete")
    public Result deleteBrand(List list){
        //商品删除
        try {
            brandService.delete(list);
            return new Result(true,"delete ok");
        }catch (Exception e){
            return new Result(false,"delete fail");
        }
    }
    @RequestMapping("/update")
    public Result updateBrand(@RequestBody Brand brand){
        //商品修改
        try {
            brandService.update(brand);
            return new Result(true,"update ok");
        }catch (Exception e){
            return new Result(false,"update fail");
        }
    }
    @RequestMapping("/test")
    public String test(){
        System.out.println("hello");
        return "hello World";
    }
}
