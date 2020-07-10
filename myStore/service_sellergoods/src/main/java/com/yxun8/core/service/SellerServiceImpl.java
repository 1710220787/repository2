package com.yxun8.core.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yxun8.core.dao.seller.SellerDao;
import com.yxun8.core.pojo.entity.PageListRes;
import com.yxun8.core.pojo.seller.Seller;
import com.yxun8.core.pojo.seller.SellerQuery;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class SellerServiceImpl implements SellerService {
    @Autowired
    private SellerDao sellerDao;

    @Override
    public void add(Seller seller) {
        sellerDao.insertSelective(seller);
    }

    @Override
    public PageListRes getAllSeller(Integer page, Integer pageSize, Seller seller) {
        Page startPage = PageHelper.startPage(page, pageSize);
        SellerQuery sellerQuery = new SellerQuery();
        SellerQuery.Criteria criteria = sellerQuery.createCriteria();
        if (seller != null){
            if (seller.getStatus() != null && !seller.getStatus().equals("")){
                criteria.andStatusEqualTo(seller.getStatus());  //添加条件
            }

            if (seller.getName() != null && !seller.getName().equals("")){
                criteria.andNameEqualTo(seller.getName());   //添加条件
            }

            if (seller.getNickName() != null && !seller.getNickName().equals("")){
                criteria.andNameEqualTo(seller.getNickName());   //添加条件
            }
        }
        List<Seller> sellerList = sellerDao.selectByExample(sellerQuery);

        PageListRes pageListRes = new PageListRes();
        pageListRes.setTotal(startPage.getTotal());
        pageListRes.setRows(sellerList);

        return pageListRes;
    }

    @Override
    public Seller getSellerById(String id) {
        Seller seller = sellerDao.selectByPrimaryKey(id);
        return seller;
    }

    @Override
    public void updateStatus(String sellerId, String status) {
        Seller seller = new Seller();
        seller.setSellerId(sellerId);
        seller.setStatus(status);

        sellerDao.updateByPrimaryKeySelective(seller);
    }
}
