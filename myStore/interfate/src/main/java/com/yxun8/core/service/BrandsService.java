package com.yxun8.core.service;

import com.yxun8.core.pojo.good.Brand;
import com.yxun8.core.pojo.entity.PageListRes;

import java.util.List;
import java.util.Map;

public interface BrandsService {
    /*查询所有品牌*/
    public List<Brand> getAllBrand();

    /**
     * 查询商品分页数据
     * @param page   当前第几条数据
     * @param pageSize   当前页有多少条数据
     * @return
     */
    PageListRes getPageBrand(Integer page, Integer pageSize,Brand brand);

    /**
     * 添加商品
     * @param brand  商品对象参数
     */
    void addBrand(Brand brand);

    /**
     * 根据id查找一个商品
     * @param id   商品id
     * @return
     */
    Brand updateById(Long id);

    /**
     * 更新操作
     * @param brand
     */
    void update(Brand brand);

    /**
     * 删除数据
     * @param idx  参数是一个数组
     */
    void delete(Long[] idx);

    /**
     * 查询全部品牌
     * @return
     */
    List<Map> selectOptionList();

    /**
     * 使用redis查询
     */
//    PageListRes allBrand(Integer page,Integer pageSize,Brand brand);

}
