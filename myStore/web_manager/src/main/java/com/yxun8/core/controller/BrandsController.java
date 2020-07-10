package com.yxun8.core.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yxun8.core.pojo.entity.Result;
import com.yxun8.core.pojo.good.Brand;
import com.yxun8.core.pojo.entity.PageListRes;
import com.yxun8.core.service.BrandsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("brand")
public class BrandsController {
    @Reference
    private BrandsService brandsService;

    /*查询所有品牌*/
    @RequestMapping("getAllBrand")
    public List<Brand> getAllBrand(){
        List<Brand> allBrand = brandsService.getAllBrand();
        return allBrand;
    }

    /*分页查询所有品牌*/
    @RequestMapping("findPage")
    public PageListRes pageBrand(Integer page,Integer pageSize,@RequestBody Brand brand){
        PageListRes pageListRes = brandsService.getPageBrand(page, pageSize,brand);
//        PageListRes pageListRes = brandsService.allBrand(page, pageSize, brand);
        return pageListRes;
    }

    /*添加品牌*/
    @RequestMapping("addBrand")
    /*这里要用RequestBody接收参数，因为在获取参数的时候参数已经被转为json字符串*/
    public Result addBrand(@RequestBody Brand brand){
        try{
            if (brand.getFirstChar().length() > 1){
                return new Result(true,"首字母不合法");
            }
            brandsService.addBrand(brand);
            return new Result(true,"添加成功");
        }catch (Exception e){
            return new Result(false,"添加失败");
        }
    }

    /*根据id查找商品*/
    @RequestMapping("updateById")
    public Brand updateById(Long id){
        Brand brand = brandsService.updateById(id);
        return brand;
    }

    /*更新操作*/
    @RequestMapping("update")
    public Result update(@RequestBody Brand brand){
        try{
            if (brand.getFirstChar().length() > 1){
                return new Result(true,"首字母不合法");
            }
            brandsService.update(brand);
            return new Result(true,"修改成功");
        }catch (Exception e){
            return new Result(false,"修改失败");
        }
    }

    /*删除记录*/
    @RequestMapping("delete")
    public Result delete(Long[] idx){
        System.out.println(idx);
        try{
            brandsService.delete(idx);
            return new Result(true,"删除成功");
        }catch (Exception e){
            return new Result(false,"删除失败");
        }
    }

    /*查询所有的品牌*/
    @RequestMapping("selectOptionList")
    public List<Map> selectOptionList(){
        List<Map> selectOptionList = brandsService.selectOptionList();
        return selectOptionList;
    }



}
