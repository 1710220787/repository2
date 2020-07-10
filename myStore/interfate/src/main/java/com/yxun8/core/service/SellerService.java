package com.yxun8.core.service;

import com.yxun8.core.pojo.entity.PageListRes;
import com.yxun8.core.pojo.seller.Seller;

public interface SellerService {
    /**
     * 申请入驻
     */
    public void add(Seller seller);

    /**
     * 审核列表
     * @param page
     * @param pageSize
     * @param seller
     * @return
     */
    PageListRes getAllSeller(Integer page, Integer pageSize, Seller seller);

    /**
     * 根据id查找
     * @param id
     * @return
     */
    Seller getSellerById(String id);

    /**
     * 审核成功
     * @param sellerId
     * @param status
     */
    void updateStatus(String sellerId, String status);
}
