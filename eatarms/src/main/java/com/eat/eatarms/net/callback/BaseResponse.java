package com.eat.eatarms.net.callback;

import java.io.Serializable;

public class BaseResponse<T> implements Serializable {
    private Integer status;
    private String info;
    private String extInfo;
    private Integer totalPages;
    private Integer totalRows;
    private T response;

    public Integer getStatus() {
        return status;
    }

    public String getInfo() {
        return info;
    }

    public String getExtInfo() {
        return extInfo;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public Integer getTotalRows() {
        return totalRows;
    }

    public T getResponse() {
        return response;
    }
}
