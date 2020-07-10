package com.yxun8.core.service;

import com.yxun8.core.pojo.item.ItemCat;

import java.util.List;

public interface ItemCatService {
    public List<ItemCat> findByParentId(Long parentId);

    ItemCat findOne(Long id);

    List<ItemCat> findAll();

}
