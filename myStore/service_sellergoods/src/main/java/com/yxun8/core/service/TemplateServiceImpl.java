package com.yxun8.core.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yxun8.core.dao.specification.SpecificationOptionDao;
import com.yxun8.core.dao.template.TypeTemplateDao;
import com.yxun8.core.pojo.entity.PageListRes;
import com.yxun8.core.pojo.good.Brand;
import com.yxun8.core.pojo.specification.SpecificationOption;
import com.yxun8.core.pojo.specification.SpecificationOptionQuery;
import com.yxun8.core.pojo.template.TypeTemplate;
import com.yxun8.core.pojo.template.TypeTemplateQuery;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service
public class TemplateServiceImpl implements TemplateService {
    @Autowired
    private TypeTemplateDao  typeTemplateDao;

    @Autowired
    private SpecificationOptionDao specificationOptionDao;

    @Override
    public PageListRes getAllTemplate(Integer page, Integer pageSize, TypeTemplate typeTemplate) {
        Page startPage = PageHelper.startPage(page, pageSize);
        TypeTemplateQuery typeTemplateQuery = new TypeTemplateQuery();
        TypeTemplateQuery.Criteria criteria = typeTemplateQuery.createCriteria();
        if (typeTemplate != null){
            if (typeTemplate.getName() != null && !typeTemplate.getName().equals("")){
                criteria.andNameLike("%" + typeTemplate.getName() + "%");
            }
        }
        List<TypeTemplate> typeTemplates = typeTemplateDao.selectByExample(typeTemplateQuery);

        PageListRes pageListRes = new PageListRes();
        pageListRes.setTotal(startPage.getTotal());
        pageListRes.setRows(typeTemplates);

        return pageListRes;
    }

    @Override
    public void save(TypeTemplate typeTemplate) {
        typeTemplateDao.insertSelective(typeTemplate);
    }

    @Override
    public TypeTemplate updateUI(Long id) {
        TypeTemplate typeTemplate = typeTemplateDao.selectByPrimaryKey(id);
        return typeTemplate;
    }

    @Override
    public void update(TypeTemplate typeTemplate) {
        typeTemplateDao.updateByPrimaryKeySelective(typeTemplate);
    }

    @Override
    public void deleteTemplate(Long[] idx) {
        for (Long id : idx) {
            typeTemplateDao.deleteByPrimaryKey(id);
        }
    }

    @Override
    public TypeTemplate findOne(Long id) {
        TypeTemplate template = typeTemplateDao.selectByPrimaryKey(id);
        return template;
    }

    @Override
    public List<Map> findBySpecId(Long id) {
        //获取所有的规格
        TypeTemplate typeTemplate = typeTemplateDao.selectByPrimaryKey(id);
        if (typeTemplate.getSpecIds() != null){
            //取出规格
            String specIds = typeTemplate.getSpecIds();  //是一个字符串的json数据
            //把specIds转为map集合   [{"id":43,"spec_name":"选择版本"},{"id":44,"spec_name":"套　　餐"}]
            List<Map> mapList = JSON.parseArray(specIds, Map.class);
            //遍历集合
            for (Map map : mapList) {
                //获取map的key
                Object specId = map.get("id");
                //把specId转为字符串
                String value = String.valueOf(specId);
                //把字符串value转为Long类型
                long SpecId = Long.parseLong(value);
                //根据规格id查找规格选项,因为SpecId不是主键，所有不能根据主键来查找
                SpecificationOptionQuery query = new SpecificationOptionQuery();
                SpecificationOptionQuery.Criteria criteria = query.createCriteria();
                criteria.andSpecIdEqualTo(SpecId);

                List<SpecificationOption> specificationOptions = specificationOptionDao.selectByExample(query);
                map.put("specificationOptions",specificationOptions);

            }
            return mapList;
        }
        return null;
    }
}
