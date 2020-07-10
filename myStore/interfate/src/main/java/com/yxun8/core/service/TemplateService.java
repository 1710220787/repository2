package com.yxun8.core.service;

import com.yxun8.core.pojo.entity.PageListRes;
import com.yxun8.core.pojo.good.Brand;
import com.yxun8.core.pojo.template.TypeTemplate;

import java.util.List;
import java.util.Map;

public interface TemplateService {
    /**
     * 所有模板数据
     * @param page
     * @param pageSize
     * @param typeTemplate
     * @return
     */
    public PageListRes getAllTemplate(Integer page, Integer pageSize, TypeTemplate typeTemplate);

    /**
     * 保存
     * @param typeTemplate
     */
    void save(TypeTemplate typeTemplate);

    /**
     * 根据id查找一条数据
     * @param id
     * @return
     */
    TypeTemplate updateUI(Long id);

    /**
     * 更新模板
     * @param typeTemplate
     */
    void update(TypeTemplate typeTemplate);

    /**
     * 删除模板
     * @param idx
     */
    void deleteTemplate(Long[] idx);

    /**
     * 获取一条数据
     * @param id
     * @return
     */
    TypeTemplate findOne(Long id);


    /**
     * 加载规格
     * @param id
     * @return
     */
    List<Map> findBySpecId(Long id);
}
