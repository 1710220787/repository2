package com.yxun8.core.service;
/**
 * 自定义SpringSecurity认证器  实现UserDetailsService接口
 */

import com.yxun8.core.pojo.entity.Result;
import com.yxun8.core.pojo.seller.Seller;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;

public class UserDetailsServiceImpl implements UserDetailsService {

    private SellerService sellerService;
    public void setSellerService(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //添加当前角色的权限  可以从数据库中去查找
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_SELLER"));
        if (username == null){
            return null;
        }
        Seller seller = sellerService.getSellerById(username);
        if (seller != null){
            //‘1’是审核通过
            if (seller.getStatus().equals("1")){
                /*参数分别是：用户名，{没有加密就一定要设置这个noop}密码，集合里面的权限*/
                return new User(username,seller.getPassword(),authorities);
            }
        }
        return null;
    }
}
