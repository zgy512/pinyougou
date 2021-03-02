package cn.itcast.core.controller;

import cn.itcast.core.pojo.ad.ContentCategory;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.service.CategoryService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/contentCategory")
public class CategoryController {

    @Reference
    private CategoryService categoryService;

    @RequestMapping("/findAll")
    public List<ContentCategory> findAll() {
        List<ContentCategory> contentCategoryList = categoryService.findAll();
        return contentCategoryList;
    }

    @RequestMapping("/findOne")
    public ContentCategory findOne(Long id) {
        ContentCategory one = categoryService.findOne(id);
        return one;
    }

    @RequestMapping("/add")
    public Result addBrand(@RequestBody ContentCategory contentCategory) {
        try {
            categoryService.add(contentCategory);
            return new Result(true, "ok");
        } catch (Exception e) {
            return new Result(false, "fail");
        }
    }

    @RequestMapping("/delete")
    public Result deleteBrand(Long[] ids) {
        try {
            categoryService.delete(ids);
            return new Result(true, "delete ok");
        } catch (Exception e) {
            return new Result(false, "delete fail");
        }
    }

    @RequestMapping("/update")
    public Result updateBrand(@RequestBody ContentCategory contentCategory) {
        try {
            categoryService.update(contentCategory);
            return new Result(true, "update ok");
        } catch (Exception e) {
            return new Result(false, "update fail");
        }
    }

    @RequestMapping("/search")
    public PageResult search(@RequestBody ContentCategory contentCategory, Integer page, Integer rows) {
        try {
            PageResult search = categoryService.search(contentCategory, page, rows);
            return search;
        }catch (Exception e){
            e.printStackTrace();
            return new PageResult("fail",0L,null);
        }
    }
}
