package cn.itcast.core.pojo.entity;

import java.io.Serializable;

public class Result <T> implements Serializable {
    public Boolean code;
    public String message;

    public Result(){}

    public Result(Boolean code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(Boolean code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Boolean getCode() {
        return code;
    }

    public void setCode(Boolean code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T data;

}
