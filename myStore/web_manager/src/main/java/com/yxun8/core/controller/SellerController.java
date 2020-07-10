package com.yxun8.core.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yxun8.core.pojo.entity.PageListRes;
import com.yxun8.core.pojo.entity.Result;
import com.yxun8.core.pojo.seller.Seller;
import com.yxun8.core.service.SellerService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("seller")
public class SellerController {
    @Reference
    private SellerService sellerService;
    @RequestMapping("findPage")
    public PageListRes findPage(Integer page, Integer pageSize,@RequestBody Seller seller){
        PageListRes pageListRes = sellerService.getAllSeller(page,pageSize,seller);
        return pageListRes;
    }

    @RequestMapping("getSellerById")
    public Seller getSellerById(String id){
        Seller seller = sellerService.getSellerById(id);
        return seller;
    }

    @RequestMapping("updateStatus")
    public Result updateStatus(String sellerId,String status){
        System.out.println(sellerId + "-----------------" +  status);
        try {
            sellerService.updateStatus(sellerId,status);
            return new Result(true,"审核通过！");
        }catch(Exception e){
            return new Result(false,"审核不通过！");
        }
    }


}
