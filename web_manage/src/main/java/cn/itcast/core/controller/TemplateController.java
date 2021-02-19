package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.template.TypeTemplate;
import cn.itcast.core.service.TemplateService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/typeTemplate")
public class TemplateController {
    @Reference
    private TemplateService templateService;

    @RequestMapping("/findAll")
    public List<TypeTemplate> findAll() {
        List<TypeTemplate> typeTemplateList = templateService.findAll();
        return typeTemplateList;
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody TypeTemplate typeTemplate, Integer page, Integer rows) {
        PageResult pageResult = templateService.search(typeTemplate, page, rows);
        return pageResult;
    }

    @RequestMapping("/findOne")
    public TypeTemplate findOne(Long id) {
        TypeTemplate typeTemplate = templateService.findOne(id);
        return typeTemplate;
    }

    @RequestMapping("/add")
    public Result addSpecification(@RequestBody TypeTemplate typeTemplate) {
        try {
            templateService.add(typeTemplate);
            return new Result(true, "添加成功");
        } catch (Exception e) {
            return new Result(false, "添加失败");
        }
    }

    @RequestMapping("/update")
    public Result updateSpecification(@RequestBody TypeTemplate typeTemplate) {
        try {
            templateService.update(typeTemplate);
            return new Result(true, "更新成功");
        } catch (Exception e) {
            return new Result(false, "更新失败");
        }
    }

    @RequestMapping("/delete")
    public Result deleteSpecification(Long[] ids) {
        try {
            templateService.delete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            return new Result(false, "删除失败");
        }
    }

    @RequestMapping("/search")
    public PageResult search(@RequestBody TypeTemplate typeTemplate, Integer page, Integer rows) {
        PageResult pageResult = templateService.search(typeTemplate, page, rows);
        return pageResult;
    }

}
