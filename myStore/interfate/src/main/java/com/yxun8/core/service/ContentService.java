package com.yxun8.core.service;

import com.yxun8.core.pojo.ad.Content;
import com.yxun8.core.pojo.ad.ContentCategory;
import com.yxun8.core.pojo.entity.PageListRes;

import java.util.List;

public interface ContentService {
    public PageListRes allContentCategory(Integer page, Integer pageSize, ContentCategory contentCategory);

    void add(ContentCategory contentCategory);

    ContentCategory updateUI(Long id);

    void update(ContentCategory contentCategory);


    PageListRes allContent(Integer page, Integer pageSize);

    List<ContentCategory> getCategory();

    void addContent(Content content);

    List<Content> findByCategoryId(Long id);

    /*存储到redis*/
    List<Content> findRedisByCategoryId(Long id);
}
