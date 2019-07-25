package com.huwei.live.request;

public class HistorySearch {

    private Integer userId;

    private String userName;

    private Integer videoId;

    private Integer currentPage;

    private Integer pageSize;

    private String orderName;

    private String orderSort;

    public HistorySearch() {
        this.orderName = "id";
        this.orderSort = "";
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
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
