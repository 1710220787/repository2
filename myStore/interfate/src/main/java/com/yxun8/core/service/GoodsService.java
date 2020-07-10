package com.yxun8.core.service;

import com.yxun8.core.pojo.entity.GoodsEntity;
import com.yxun8.core.pojo.entity.PageListRes;
import com.yxun8.core.pojo.good.Goods;

public interface GoodsService {
    public void add(GoodsEntity goodsEntity);

    PageListRes findPage(Goods goods, Integer page, Integer rows);
}
