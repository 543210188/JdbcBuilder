package com.ybchen.build.core;


import java.util.List;

/**
 * 分页工具
 *
 * @param <T>
 */
public class Pages<T> {
    /**
     * 每一页保存的数据
     */
    private List<T> list;

    /**
     * 总记录条数
     */
    private int total;

    /**
     * 每一页的记录条数
     */
    private int pageSize;

    /**
     * 所有的页数
     */
    private int totalPages;

    /**
     * 当前页数
     */
    private int currentPage;

    /**
     * @param total
     * @param currentPage
     * @param pageSize
     */
    public Pages(int total, int currentPage, int pageSize,List list) {
        this.total = total;
        this.pageSize = pageSize;
        this.list=list;
        this.totalPages = (this.total - 1) / this.pageSize + 1;
        if (currentPage < 1) {
            this.currentPage = 1;
        } else if (currentPage > this.totalPages) {
            this.currentPage = this.totalPages;
        } else {
            this.currentPage = currentPage;
        }
    }

    public List<T> getList() {
        return list;
    }

    public int getTotal() {
        return total;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }
}