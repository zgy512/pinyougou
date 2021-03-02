package cn.itcast.core.controller;

import cn.itcast.core.pojo.ad.Content;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.service.ContentService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/content")
public class ContentController {

    @Reference
    private ContentService contentService;
    @RequestMapping("/findAll")
    public List<Content> findAll() {
        List<Content> contentList = contentService.findAll();
        return contentList;
    }

    @RequestMapping("/findOne")
    public Content findOne(Long id) {
        Content one = contentService.findOne(id);
        return one;
    }

    @RequestMapping("/add")
    public Result addBrand(@RequestBody Content content) {
        //商品添加
        try {
            contentService.add(content);
            return new Result(true, "ok");
        } catch (Exception e) {
            return new Result(false, "fail");
        }
    }

    @RequestMapping("/delete")
    public Result deleteBrand(Long[] ids) {
        try {
            contentService.delete(ids);
            return new Result(true, "delete ok");
        } catch (Exception e) {
            return new Result(false, "delete fail");
        }
    }

    @RequestMapping("/update")
    public Result updateBrand(@RequestBody Content content) {
        try {
            contentService.update(content);
            return new Result(true, "update ok");
        } catch (Exception e) {
            return new Result(false, "update fail");
        }
    }

    @RequestMapping("/search")
    public PageResult search(@RequestBody Content content, Integer page, Integer rows) {
        try {
            PageResult search = contentService.search(content, page, rows);
            return search;
        }catch (Exception e){
            e.printStackTrace();
            return new PageResult("fail",0L,null);
        }
    }
}
