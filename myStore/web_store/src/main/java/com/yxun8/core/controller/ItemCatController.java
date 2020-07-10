package com.yxun8.core.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yxun8.core.pojo.item.ItemCat;
import com.yxun8.core.service.ItemCatService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("itemCat")
public class ItemCatController {
    @Reference
    private ItemCatService itemCatService;

    @RequestMapping("findByParentId")
    public List<ItemCat> findByParentId(Long parentId){
        List<ItemCat> cats = itemCatService.findByParentId(parentId);
        return cats;
    }

    @RequestMapping("findOne")
    public ItemCat findOne(Long id){
        ItemCat allItem = itemCatService.findOne(id);
        return allItem;
    }

    @RequestMapping("findAllCategory")
    public List<ItemCat> findAllCategory(){
        return itemCatService.findAll();
    }

}
