package com.huwei.live.request;

public class MessageSearch {

    private Integer currentPage;

    private Integer pageSize;

    private String orderName;

    private String orderSort;

    public MessageSearch() {
        this.orderName = "id";
        this.orderSort = "";
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderSort() {
        return orderSort;
    }

    public void setOrderSort(String orderSort) {
        this.orderSort = orderSort;
    }
}
