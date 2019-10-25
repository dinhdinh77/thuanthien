package com.farm.dinh.remote;

public class StandardResponse<T> extends SimpleResponse {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
