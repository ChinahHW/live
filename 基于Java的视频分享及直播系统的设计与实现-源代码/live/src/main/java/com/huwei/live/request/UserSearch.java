package com.huwei.live.request;

/**
 * Description:
 *
 * @author zlp
 * @create 2018-09-12 16:14
 **/
public class UserSearch {

    private String userName;

    private Integer currentPage;

    private Integer pageSize;

    private String orderName;

    private String orderSort;

    public UserSearch(){
        this.orderName = "id";
        this.orderSort = "";
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
        char[] order = orderName.toCharArray();
        String newName = "";
        for (int i=0;i<order.length;i++){
            if(Character.isUpperCase(order[i])){
                newName = newName + "_"+Character.toLowerCase(order[i]);
            }else{
                newName = newName + order[i];
            }
        }
        this.orderName = newName;
    }

    public String getOrderSort() {
        return orderSort;
    }

    public void setOrderSort(String orderSort) {
        this.orderSort = orderSort;
    }


}
