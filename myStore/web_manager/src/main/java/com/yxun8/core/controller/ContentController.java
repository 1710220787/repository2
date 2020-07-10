package com.yxun8.core.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yxun8.core.pojo.ad.Content;
import com.yxun8.core.pojo.ad.ContentCategory;
import com.yxun8.core.pojo.entity.PageListRes;
import com.yxun8.core.pojo.entity.Result;
import com.yxun8.core.service.ContentService;
import com.yxun8.core.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("contentCategory")
public class ContentController {
    @Reference
    private ContentService contentService;

    @RequestMapping("search")
    public PageListRes search(Integer page, Integer pageSize, @RequestBody ContentCategory contentCategory){
        PageListRes pageListRes = contentService.allContentCategory(page, pageSize, contentCategory);
        return pageListRes;
    }

    @RequestMapping("add")
    public Result add(@RequestBody ContentCategory contentCategory){
        try {
            contentService.add(contentCategory);
            return new Result(true,"添加成功");
        }catch (Exception e){
            return new Result(false,"添加失败");
        }
    }

    @RequestMapping("findOne")
    public ContentCategory updateUI(Long id){
        ContentCategory contentCategory = contentService.updateUI(id);
        System.out.println(contentCategory);
        return contentCategory;
    }

    @RequestMapping("update")
    public Result update(@RequestBody ContentCategory contentCategory){
        try {
            contentService.update(contentCategory);
            return new Result(true,"修改成功");
        }catch (Exception e){
            return new Result(false,"修改失败");
        }
    }


    @RequestMapping("searchContent")
    public PageListRes searchContent(Integer page, Integer pageSize){
        PageListRes pageListRes = contentService.allContent(page, pageSize);
        return pageListRes;
    }

    @RequestMapping("findAll")
    public List<ContentCategory> getCategory(){
        List<ContentCategory> categoryList = contentService.getCategory();
        return categoryList;
    }

    @RequestMapping("addContent")
    public Result addContent(@RequestBody Content content){
        try {
            contentService.addContent(content);
            return new Result(true,"保存成功");
        }catch (Exception e){
            return new Result(false,"保存失败");
        }
    }
}
