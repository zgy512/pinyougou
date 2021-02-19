package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.entity.SpecEntity;
import cn.itcast.core.pojo.specification.Specification;
import cn.itcast.core.service.SpecifitionService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 规格的控制器
 */

@RestController
@RequestMapping("/specification")
public class SpecificationController {
    @Reference
    private SpecifitionService specifitionService;

    @RequestMapping("/findAll")
    public List<Specification> findAll() {
        List<Specification> specificationList = specifitionService.findAll();
        return specificationList;
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody Specification specification, Integer page, Integer rows) {
        PageResult pageResult = specifitionService.search(specification, page, rows);
        return pageResult;
    }

    @RequestMapping("/findOne")
    public SpecEntity findOne(Long id) {
        SpecEntity specEntity = specifitionService.findOne(id);
        return specEntity;
    }

    @RequestMapping("/add")
    public Result addSpecification(@RequestBody SpecEntity specEntity) {
        try {
            specifitionService.add(specEntity);
            return new Result(true, "添加成功");
        } catch (Exception e) {
            return new Result(false, "添加失败");
        }
    }

    @RequestMapping("/update")
    public Result updateSpecification(@RequestBody SpecEntity specEntity) {
        try {
            specifitionService.update(specEntity);
            return new Result(true, "更新成功");
        } catch (Exception e) {
            return new Result(false, "更新失败");
        }
    }

    @RequestMapping("/delete")
    public Result deleteSpecification(Long[] ids) {
        try {
            specifitionService.delete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            return new Result(false, "删除失败");
        }
    }

    @RequestMapping("/search")
    public PageResult search(@RequestBody Specification specification, Integer page, Integer rows) {
        PageResult pageResult = specifitionService.search(specification, page, rows);
        return pageResult;
    }

    @RequestMapping("/selectOptionList")
    public List<Map> selectOptionList() {
        List<Map> maps = specifitionService.selectOptionList();
        return maps;
    }
}
