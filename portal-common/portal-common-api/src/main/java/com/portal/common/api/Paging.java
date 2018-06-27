package com.portal.common.api;

import com.github.pagehelper.StringUtil;

import java.io.Serializable;
import java.util.Map;

/**
 * 分页对象
 *
 * @author zhangxd
 */
public class Paging implements Serializable {

    /**
     * 页码
     */
    private int pageNum;
    private int currentPage;
    /**
     * 每页条数
     */
    private int pageSize;
    /**
     * 排序字段
     */
    private String orderBy;

    public Paging() {
        this.pageNum = 1;
        this.pageSize = 20;
    }

    public Paging(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public Paging(int pageNum, int pageSize, String orderBy) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.orderBy = orderBy;
    }

    public Paging(Map<String, Object> param) {
        this.pageNum = 1;
        this.pageSize = 20;

        Object pageNum = param.get("pageNum");
        if(pageNum != null && StringUtil.isNotEmpty(pageNum.toString())) {
            this.pageNum = Integer.valueOf(pageNum.toString());
        }
        Object pageSize = param.get("pageSize");
        if(pageSize != null && StringUtil.isNotEmpty(pageSize.toString())) {
            this.pageSize = Integer.valueOf(pageSize.toString());
        }
        Object orderBy = param.get("orderBy");
        if(orderBy != null && StringUtil.isNotEmpty(orderBy.toString())) {
            this.orderBy = orderBy.toString();
        }
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
    public int getCurrentPage() {
        return pageNum;
    }

    public void setCurrentPage(int currentPage) {
        this.pageNum = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

}
