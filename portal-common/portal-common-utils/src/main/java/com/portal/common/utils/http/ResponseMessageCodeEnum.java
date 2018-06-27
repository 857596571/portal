package com.portal.common.utils.http;

public enum ResponseMessageCodeEnum {

    SUCCESS(0),
    ERROR(1),
    VALID_ERROR(1000);

    private int code;

    ResponseMessageCodeEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
