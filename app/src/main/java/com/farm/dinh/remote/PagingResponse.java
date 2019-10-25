package com.farm.dinh.remote;

public class PagingResponse<T> extends StandardResponse<T> {
    private int totalPage;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
