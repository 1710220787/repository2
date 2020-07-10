package com.yxun8.core.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yxun8.core.pojo.entity.PageListRes;
import com.yxun8.core.pojo.entity.Result;
import com.yxun8.core.pojo.template.TypeTemplate;
import com.yxun8.core.service.TemplateService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("template")
public class TemplateController {
    @Reference
    private TemplateService templateService;

    @RequestMapping("getAllTemplate")
    public PageListRes getAllTemplate(Integer page, Integer pageSize,@RequestBody TypeTemplate typeTemplate){
        PageListRes allTemplate = templateService.getAllTemplate(page, pageSize, typeTemplate);
        return allTemplate;
    }

    /*保存*/
    @RequestMapping("save")
    public Result save(@RequestBody TypeTemplate typeTemplate){
        try {
            templateService.save(typeTemplate);
            return new Result(true,"保存成功");
        }catch (Exception e){
            return new Result(false,"保存失败");
        }
    }

    /*根据id查找一条数据*/
    @RequestMapping("updateUI")
    public TypeTemplate updateUI(Long id){
        TypeTemplate typeTemplate = templateService.updateUI(id);
        return typeTemplate;
    }

    /*更新操作*/
    @RequestMapping("update")
    public Result update(@RequestBody TypeTemplate typeTemplate){
        System.out.println(typeTemplate);
        try {
            templateService.update(typeTemplate);
            return new Result(true,"修改成功");
        }catch (Exception e){
            return new Result(false,"修改失败");
        }
    }

    /*删除*/
    @RequestMapping("deleteTemplate")
    public Result deleteTemplate(Long[] idx){
        try {
            templateService.deleteTemplate(idx);
            return new Result(true,"删除成功");
        }catch (Exception e){
            return new Result(false,"删除失败");
        }
    }
}
