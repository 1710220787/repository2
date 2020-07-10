package com.yxun8.core.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sun.org.apache.regexp.internal.RE;
import com.yxun8.core.pojo.entity.PageListRes;
import com.yxun8.core.pojo.entity.Result;
import com.yxun8.core.pojo.entity.SpecEntity;
import com.yxun8.core.pojo.specification.Specification;
import com.yxun8.core.service.SpecService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("spec")
public class SpecController {
    @Reference
    private SpecService specService;

    /*查询全部规格数据*/
    @RequestMapping("getAllSpec")
    public PageListRes getAllSpec(Integer page, Integer pageSize, @RequestBody Specification specification){
        //调用业务
        PageListRes pageListRes = specService.getAllSpec(page,pageSize,specification);
        return pageListRes;
    }

    /*保存规格数据*/
    @RequestMapping("save")
    public Result save(@RequestBody SpecEntity specEntity){
        try {
            specService.save(specEntity);
            return new Result(true,"保存成功");
        }catch (Exception e){
            return new Result(false,"保存失败");
        }

    }

    /*查找一条规格和对应的规格选项*/
    @RequestMapping("getSpecById")
    public SpecEntity getSpecById(Long id){
        SpecEntity specEntity = specService.getSpecById(id);
        return specEntity;
    }

    /*更新*/
    @RequestMapping("update")
    public Result update(@RequestBody SpecEntity specEntity){
        try {
            specService.update(specEntity);
            return new Result(true,"更新成功");
        }catch (Exception e){
            return new Result(false,"保存失败");
        }
    }

    /*删除*/
    @RequestMapping("deleteSpec")
    public Result deleteSpec(Long[] idx){
        System.out.println(idx);
        try {
            specService.delete(idx);
            return new Result(true,"删除成功");
        }catch (Exception e){
            return new Result(false,"删除失败");
        }
    }

    @RequestMapping("selectOptionList")
    public List<Map> selectOptionList(){
        List<Map> selectOptionList = specService.selectOptionList();
        return selectOptionList;
    }
}
