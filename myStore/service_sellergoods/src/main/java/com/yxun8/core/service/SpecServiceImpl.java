package com.yxun8.core.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yxun8.core.dao.specification.SpecificationDao;
import com.yxun8.core.dao.specification.SpecificationOptionDao;
import com.yxun8.core.pojo.entity.PageListRes;
import com.yxun8.core.pojo.entity.SpecEntity;
import com.yxun8.core.pojo.specification.Specification;
import com.yxun8.core.pojo.specification.SpecificationOption;
import com.yxun8.core.pojo.specification.SpecificationOptionQuery;
import com.yxun8.core.pojo.specification.SpecificationQuery;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class SpecServiceImpl implements SpecService {
    /*注入规格数据库*/
    @Autowired
    private SpecificationDao specificationDao;
    /*注入规格选项数据库*/
    @Autowired
    private SpecificationOptionDao specificationOptionDao;


    @Override
    public PageListRes getAllSpec(Integer page, Integer pageSize, Specification specification) {
        Page startPage = PageHelper.startPage(page, pageSize);
        SpecificationQuery specificationQuery = new SpecificationQuery();
        SpecificationQuery.Criteria criteria = specificationQuery.createCriteria();
        if (specification != null){
            if (specification.getSpecName() != null && !specification.getSpecName().equals("")){
                criteria.andSpecNameLike("%"+specification.getSpecName()+"%");
            }
        }
        List<Specification> list = specificationDao.selectByExample(specificationQuery);
        PageListRes pageListRes = new PageListRes();
        pageListRes.setTotal(startPage.getTotal());
        pageListRes.setRows(list);
        return pageListRes;
    }

    @Override
    public void save(SpecEntity specEntity) {
        System.out.println(specEntity);
        //保存规格
        specificationDao.insertSelective(specEntity.getSpec());
        for (SpecificationOption specificationOption : specEntity.getSpecOption()) {
            //根据规格id设置规格和规格选项的关系
            specificationOption.setSpecId(specEntity.getSpec().getId());
            //保存规格选项
            specificationOptionDao.insertSelective(specificationOption);
        }
    }

    @Override
    public SpecEntity getSpecById(Long id) {
        //先查找规格
        Specification specification = specificationDao.selectByPrimaryKey(id);

        //再根据规格的id去查找规格选项
        SpecificationOptionQuery specificationOptionQuery = new SpecificationOptionQuery();
        SpecificationOptionQuery.Criteria criteria = specificationOptionQuery.createCriteria();
        criteria.andSpecIdEqualTo(specification.getId());
        List<SpecificationOption> specificationOptionList = specificationOptionDao.selectByExample(specificationOptionQuery);

        //再把查出来的封装成一个对象返回出去
        SpecEntity specEntity = new SpecEntity();
        specEntity.setSpec(specification);
        specEntity.setSpecOption(specificationOptionList);
        System.out.println(specEntity);
        return specEntity;
    }

    @Override
    public void update(SpecEntity specEntity) {
        //更新规格
        specificationDao.updateByPrimaryKey(specEntity.getSpec());

        //更新规格id打破原来的关系  id删除对象的关系
        SpecificationOptionQuery query = new SpecificationOptionQuery();
        SpecificationOptionQuery.Criteria criteria = query.createCriteria();
        //规格id等于规格选项id  想当于s.id = so.id
        criteria.andSpecIdEqualTo(specEntity.getSpec().getId());
        specificationOptionDao.deleteByExample(query);

        //重新建立关系
        for (SpecificationOption specificationOption : specEntity.getSpecOption()) {
            //根据规格id设置规格和规格选项的关系
            specificationOption.setSpecId(specEntity.getSpec().getId());
            //保存规格选项
            specificationOptionDao.insertSelective(specificationOption);
        }
    }

    @Override
    public void delete(Long[] idx) {
        //删除规格
        for (Long id : idx) {
            specificationDao.deleteByPrimaryKey(id);
            //根据传入来的id删除规格选项  因为不是根据主键删除，所有要穿一个SpecificationOptionQuery对象来进行删除
            SpecificationOptionQuery query = new SpecificationOptionQuery();
            SpecificationOptionQuery.Criteria queryCriteria = query.createCriteria();
            queryCriteria.andSpecIdEqualTo(id);
            specificationOptionDao.deleteByExample(query);
        }
    }

    @Override
    public List<Map> selectOptionList() {
        List<Map> selectOptionList = specificationDao.selectOptionList();
        return selectOptionList;
    }
}
