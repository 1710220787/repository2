package com.yxun8.core.service;

import com.yxun8.core.pojo.entity.PageListRes;
import com.yxun8.core.pojo.entity.SpecEntity;
import com.yxun8.core.pojo.specification.Specification;

import java.util.List;
import java.util.Map;

public interface SpecService {
    /**
     * 查找全部规格
     * @param page
     * @param pageSize
     * @param specification
     * @return
     */
    PageListRes getAllSpec(Integer page, Integer pageSize, Specification specification);

    /**
     * 保存规格
     * @param specEntity  规格的名称和规格选项
     */
    void save(SpecEntity specEntity);

    /**
     * 根据id查找对应的规格和规格选项
     * @param id
     * @return
     */
    SpecEntity getSpecById(Long id);

    /**
     * 更新
     * @param specEntity
     */
    void update(SpecEntity specEntity);

    /**
     * 删除数据
     * @param idx  参数是一个数组
     */
    void delete(Long[] idx);

    /**
     * 查询下拉规格
     * @return
     */
    List<Map> selectOptionList();

}
