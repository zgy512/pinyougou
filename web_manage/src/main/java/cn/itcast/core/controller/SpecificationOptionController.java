package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.specification.SpecificationOption;
import cn.itcast.core.service.SpecificationOptionService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/specificationOption")
public class SpecificationOptionController {
    @Reference
    private SpecificationOptionService specificationOptionService;

    @RequestMapping("/findAll")
    public List<SpecificationOption> findAll(){
        List<SpecificationOption> specificationOptionList = specificationOptionService.findAll();
        return specificationOptionList;
    }
    @RequestMapping("/search")
    public PageResult search(SpecificationOption specificationOption, Integer page, Integer rows){
        PageResult pageResult = specificationOptionService.search(specificationOption,page,rows);
        return pageResult;
    }
    @RequestMapping("/findOne")
    public SpecificationOption findOne(Long id){
        SpecificationOption specificationOption = specificationOptionService.findOne(id);
        return specificationOption;
    }
    @RequestMapping("/add")
    public Result add(@RequestBody SpecificationOption specificationOption){
        try {
            specificationOptionService.add(specificationOption);
            return new Result(true,"添加成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"添加失败");
        }
    }
    @RequestMapping("/update")
    public Result update(@RequestBody SpecificationOption specificationOption){
        try {
            specificationOptionService.update(specificationOption);
            return new Result(true,"更新成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"更新失败");
        }
    }
    @RequestMapping("/delete")
    public Result delete(Long[] ids){
        try {
            specificationOptionService.delete(ids);
            return new Result(true,"删除成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"删除失败");
        }
    }


}
