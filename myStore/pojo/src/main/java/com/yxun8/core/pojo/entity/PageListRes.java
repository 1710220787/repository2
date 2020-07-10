package com.yxun8.core.pojo.entity;

import java.io.Serializable;
import java.util.List;

public class PageListRes implements Serializable {
    /*总记录*/
    private Long total;
    /*数据*/
    private List rows;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "PageListRes{" +
                "total=" + total +
                ", rows=" + rows +
                '}';
    }
}
