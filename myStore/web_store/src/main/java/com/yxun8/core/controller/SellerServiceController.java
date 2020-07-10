package com.yxun8.core.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yxun8.core.pojo.entity.Result;
import com.yxun8.core.pojo.seller.Seller;
import com.yxun8.core.service.SellerService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("seller")
public class SellerServiceController {
    /*注入service*/
    @Reference
    private SellerService sellerService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("register")
    public Result add(@RequestBody Seller seller){
        try {
            String password = seller.getPassword();
            String encode = passwordEncoder.encode(password);
            seller.setPassword(encode);
            sellerService.add(seller);
            return new Result(true,"注册成功，请等待运营商审核！");
        }catch (Exception e){
            return new Result(false,"注册失败！");
        }
    }




}
