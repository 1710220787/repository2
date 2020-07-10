package com.yxun8.core.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yxun8.core.pojo.ad.Content;
import com.yxun8.core.pojo.ad.ContentCategory;
import com.yxun8.core.pojo.entity.PageListRes;
import com.yxun8.core.pojo.entity.Result;
import com.yxun8.core.service.ContentService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("contentCategory")
public class ContentController {
    @Reference
    private ContentService contentService;

    @RequestMapping("findByCategoryId")
    public List<Content> findByCategoryId(Long id){
        return contentService.findRedisByCategoryId(id);
    }
}
