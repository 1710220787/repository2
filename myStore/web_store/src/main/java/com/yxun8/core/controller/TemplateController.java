package com.yxun8.core.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yxun8.core.pojo.entity.PageListRes;
import com.yxun8.core.pojo.entity.Result;
import com.yxun8.core.pojo.good.Brand;
import com.yxun8.core.pojo.template.TypeTemplate;
import com.yxun8.core.service.TemplateService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("template")
public class TemplateController {
    @Reference
    private TemplateService templateService;

    @RequestMapping("findOne")
    public TypeTemplate findOne(Long id){
        TypeTemplate typeTemplate = templateService.findOne(id);
        return typeTemplate;
    }

    /*加载规格*/
    @RequestMapping("findBySpecId")
    public List<Map> findBySpecId(Long id){
        List<Map> res = templateService.findBySpecId(id);
        return res;
    }

}
