package com.portal.common.utils.http.bean;


import com.portal.common.utils.http.PageInfo;

public class BaseQueryCondition {

    private Integer pageNo;
    private Integer pageSize;
    private String orderBy;
    private Integer orderByType;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public Integer getOrderByType() {
        return orderByType;
    }

    public void setOrderByType(Integer orderByType) {
        this.orderByType = orderByType;
    }

    public PageInfo getPageInfo() {
        return new PageInfo(pageNo, pageSize);
    }

}
