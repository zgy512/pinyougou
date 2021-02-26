package cn.itcast.core.controller;

import cn.itcast.core.pojo.ad.Content;
import cn.itcast.core.service.CategoryService;
import cn.itcast.core.service.ContentService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/content")
public class ContentController {

    @Reference
    private ContentService contentService;
    @RequestMapping("/findByCategoryId")
    public List<Content> findByCategoryId(Long id){
        List<Content> contents = contentService.findByCategoryId(id);
        return contents;
    }
}
