package com.yxun8.core.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.yxun8.core.dao.item.ItemCatDao;
import com.yxun8.core.pojo.item.ItemCat;
import com.yxun8.core.pojo.item.ItemCatQuery;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private ItemCatDao itemCatDao;
    @Override
    public List<ItemCat> findByParentId(Long parentId) {
        ItemCatQuery itemCatQuery = new ItemCatQuery();
        ItemCatQuery.Criteria criteria = itemCatQuery.createCriteria();
        /*在sql语句后面添加条件  parentid=0*/
        criteria.andParentIdEqualTo(parentId);
        List<ItemCat> itemCats = itemCatDao.selectByExample(itemCatQuery);
        return itemCats;
    }

    @Override
    public ItemCat findOne(Long id) {
        ItemCat itemCat = itemCatDao.selectByPrimaryKey(id);
        return itemCat;
    }

    @Override
    public List<ItemCat> findAll() {
        List<ItemCat> cats = itemCatDao.selectByExample(null);
        return cats;
    }

}
