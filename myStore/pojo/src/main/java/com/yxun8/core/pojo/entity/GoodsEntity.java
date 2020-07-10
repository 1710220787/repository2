package com.yxun8.core.pojo.entity;

import com.yxun8.core.pojo.good.Goods;
import com.yxun8.core.pojo.good.GoodsDesc;
import com.yxun8.core.pojo.item.Item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GoodsEntity implements Serializable {
    private Goods goods;
    private GoodsDesc goodsDesc;
    private List<Item> itemList = new ArrayList<Item>();

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public GoodsDesc getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(GoodsDesc goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    @Override
    public String toString() {
        return "GoodsEntity{" +
                "goods=" + goods +
                ", goodsDesc=" + goodsDesc +
                ", itemList=" + itemList +
                '}';
    }
}
