package com.portal.common.utils.http;

public class Result {

    private static final ResponseMessage RESPONSE_MESSAGE_SUCCESS = new ResponseMessage(ResponseMessageCodeEnum.SUCCESS.getCode(), "");

    public static ResponseMessage success() {
        return RESPONSE_MESSAGE_SUCCESS;
    }

    public static <T> ResponseMessage<T> success(T t) {
        return new ResponseMessage(ResponseMessageCodeEnum.SUCCESS.getCode(), "", t);
    }

    public static ResponseMessage error() {
        return error(ResponseMessageCodeEnum.ERROR.getCode(), null);
    }

    public static ResponseMessage error(String message) {
        return error(ResponseMessageCodeEnum.ERROR.getCode(), message);
    }

    public static ResponseMessage error(Object code, String message) {
        return error(code, message, null);
    }

    public static <T> ResponseMessage<T> error(Object code, String message, T t) {
        return new ResponseMessage(code, message, t);
    }
}
