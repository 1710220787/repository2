package com.yxun8.core.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yxun8.core.dao.good.BrandDao;
import com.yxun8.core.pojo.good.Brand;
import com.yxun8.core.pojo.entity.PageListRes;
import com.yxun8.core.pojo.good.BrandQuery;
import com.yxun8.core.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BrandsServiceImpl implements BrandsService {
    /*注入dao*/
    @Autowired
    private BrandDao brandDao;

    @Autowired
    private RedisTemplate redisTemplate;


    /*查询所有品牌*/
    @Override
    public List<Brand> getAllBrand() {
        List<Brand> brandList = brandDao.selectByExample(null);
        return brandList;
    }

    @Override
    public PageListRes getPageBrand(Integer page, Integer pageSize,Brand brand) {
        Page<Brand> startPage = PageHelper.startPage(page, pageSize);
        //排序
        BrandQuery brandQuery = new BrandQuery();
        brandQuery.setOrderByClause("id desc");

        //创建离线条件查询
        BrandQuery.Criteria criteria = brandQuery.createCriteria();
        //判断搜索框是否有值
        if (brand != null){
            if (brand.getName() != null && !brand.getName().equals("")){
                //添加条件
                criteria.andNameLike("%" + brand.getName() + "%" );
            }
            if (brand.getFirstChar() != null && !brand.getFirstChar().equals("")){
                criteria.andFirstCharLike("%" +brand.getFirstChar()+ "%");
            }
        }

        List<Brand> brands = brandDao.selectByExample(brandQuery);
        PageListRes pageListRes = new PageListRes();
        pageListRes.setRows(brands);
        pageListRes.setTotal(startPage.getTotal());

//        Page<Brand> brandPage = (Page<Brand>) brandDao.selectByExample(null);
//        PageListRes pageListRes = new PageListRes();
//        pageListRes.setRows(brandPage.getResult());
//        pageListRes.setTotal(brandPage.getTotal());

        return pageListRes;
    }

    @Override
    public void addBrand(Brand brand) {
        /*
        insertSelective会去做一下判断
        insert不会做判断
         */
        brandDao.insertSelective(brand);
    }

    @Override
    public Brand updateById(Long id) {
        Brand brand = brandDao.selectByPrimaryKey(id);
        return brand;
    }

    @Override
    public void update(Brand brand) {
        brandDao.updateByPrimaryKeySelective(brand);
    }

    @Override
    public void delete(Long[] idx) {
        /*方法1*/
        /*逆向工程生成的每个都有一个Query这个东西，可以用它来创建离线条件查询来添加条件*/
        BrandQuery brandQuery = new BrandQuery();
        BrandQuery.Criteria criteria = brandQuery.createCriteria();
        criteria.andIdIn(Arrays.asList(idx));
        brandDao.deleteByExample(brandQuery);

        /*方法2*/
        //遍历id
//        if (idx != null){
//            for (Long id : idx) {
//                brandDao.deleteByPrimaryKey(id);
//            }
//        }
    }

    @Override
    public List<Map> selectOptionList() {
        List<Map> selectOptionList = brandDao.selectOptionList();
        return selectOptionList;
    }


}
