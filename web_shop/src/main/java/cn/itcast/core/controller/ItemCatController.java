package cn.itcast.core.controller;

import cn.itcast.core.pojo.item.ItemCat;
import cn.itcast.core.service.ItemCatService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/itemCat")
public class ItemCatController {

    @Reference
    private ItemCatService itemCatService;

    @RequestMapping("/findAll")
    public List<ItemCat> findAll(){
        List<ItemCat> itemCatList= itemCatService.findAll();
        return itemCatList;
    }

    @RequestMapping("/findOne")
    public ItemCat findOne(Long id){
        ItemCat itemCat = itemCatService.findOne(id);
        return itemCat;
    }
    @RequestMapping("/findByParentId")
    public List<ItemCat> findByParentId(Long parentId){
        return itemCatService.findByParentId(parentId);
    }
}
