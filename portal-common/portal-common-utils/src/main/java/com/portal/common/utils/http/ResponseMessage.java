package com.portal.common.utils.http;

public class ResponseMessage<T> {

    private Object code;
    private String message;
    private T data;

    public ResponseMessage() {
    }

    public ResponseMessage(Object code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseMessage(Object code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Object getCode() {
        return code;
    }

    public void setCode(Object code) {
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

    public boolean isOk() {
        return this.code.equals(ResponseMessageCodeEnum.SUCCESS.getCode());
    }
}
