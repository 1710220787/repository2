package com.yxun8.core.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yxun8.core.pojo.entity.GoodsEntity;
import com.yxun8.core.pojo.entity.PageListRes;
import com.yxun8.core.pojo.entity.Result;
import com.yxun8.core.pojo.good.Goods;
import com.yxun8.core.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("goods")
public class GoodsController {
    @Reference
    private GoodsService goodsService;

    @RequestMapping("add")
    public Result add(@RequestBody GoodsEntity goodsEntity){
        System.out.println(goodsEntity);
        try {
            //获取登录的用户名
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            goodsEntity.getGoods().setSellerId(name);
            goodsService.add(goodsEntity);
            return new Result(true,"保存成功");
        }catch (Exception e){
            return new Result(false,"保存失败");
        }
    }

    @RequestMapping("/search")
    public PageListRes search(@RequestBody Goods goods, Integer page , Integer pageSize) {
        //获取当前登录用户的用户名
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        goods.setSellerId(userName);
        PageListRes result = goodsService.findPage(goods, page, pageSize);
        return result;
    }
}
