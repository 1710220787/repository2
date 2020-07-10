package com.yxun8.core.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yxun8.core.dao.ad.ContentCategoryDao;
import com.yxun8.core.dao.ad.ContentDao;
import com.yxun8.core.pojo.ad.Content;
import com.yxun8.core.pojo.ad.ContentCategory;
import com.yxun8.core.pojo.ad.ContentCategoryQuery;
import com.yxun8.core.pojo.ad.ContentQuery;
import com.yxun8.core.pojo.entity.PageListRes;
import com.yxun8.core.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    private ContentCategoryDao contentCategoryDao;

    @Autowired
    private ContentDao contentDao;

    @Autowired
    private RedisTemplate redisTemplate;


    /*广告分类管理*/
    @Override
    public PageListRes allContentCategory(Integer page, Integer pageSize, ContentCategory contentCategory) {
        //分页
        Page startPage = PageHelper.startPage(page, pageSize);
        ContentCategoryQuery contentCategoryQuery = new ContentCategoryQuery();
        contentCategoryQuery.setOrderByClause("id desc");
        ContentCategoryQuery.Criteria criteria = contentCategoryQuery.createCriteria();
        if (contentCategory != null){
            if (contentCategory.getName() != null && !contentCategory.getName().equals("")){
                criteria.andNameLike("%" + contentCategory.getName() + "%");
            }
        }

        List<ContentCategory> categoryList = contentCategoryDao.selectByExample(contentCategoryQuery);

        PageListRes pageListRes = new PageListRes();
        pageListRes.setTotal(startPage.getTotal());
        pageListRes.setRows(categoryList);
        return pageListRes;
    }

    @Override
    public void add(ContentCategory contentCategory) {
        contentCategoryDao.insertSelective(contentCategory);
    }

    @Override
    public ContentCategory updateUI(Long id) {
        ContentCategory contentCategory = contentCategoryDao.selectByPrimaryKey(id);
        return contentCategory;
    }

    @Override
    public void update(ContentCategory contentCategory) {
        contentCategoryDao.updateByPrimaryKeySelective(contentCategory);
    }


    /*广告管理*/

    @Override
    public PageListRes allContent(Integer page, Integer pageSize) {
        //分页
        Page startPage = PageHelper.startPage(page, pageSize);
        ContentQuery contentQuery = new ContentQuery();

        List<Content> contents = contentDao.selectByExample(contentQuery);

        PageListRes pageListRes = new PageListRes();
        pageListRes.setTotal(startPage.getTotal());
        pageListRes.setRows(contents);
        return pageListRes;
    }

    @Override
    public List<ContentCategory> getCategory() {
        List<ContentCategory> categoryList = contentCategoryDao.selectByExample(null);
        return categoryList;
    }

    @Override
    public void addContent(Content content) {
        contentDao.insertSelective(content);
        redisTemplate.boundHashOps(Constants.CONTENT_LIST_REDIS).delete(content.getCategoryId());
    }

    @Override
    public List<Content> findByCategoryId(Long id) {
        ContentQuery contentQuery = new ContentQuery();
        ContentQuery.Criteria criteria = contentQuery.createCriteria();
        criteria.andCategoryIdEqualTo(id);
        List<Content> contents = contentDao.selectByExample(contentQuery);
        return contents;
    }

    @Override
    public List<Content> findRedisByCategoryId(Long id) {
        List<Content> contentList = (List<Content>) redisTemplate.boundHashOps(Constants.CONTENT_LIST_REDIS).get(id);
        if (contentList == null){
            contentList = this.findByCategoryId(id);
            redisTemplate.boundHashOps(Constants.CONTENT_LIST_REDIS).put(id,contentList);
        }
        return contentList;
    }


}
