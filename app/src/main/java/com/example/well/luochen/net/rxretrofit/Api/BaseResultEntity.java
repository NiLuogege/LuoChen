package com.example.well.luochen.net.rxretrofit.Api;

/**
 * 回调信息统一封装类  封装了和后台约定的每个请求共有的部分
 */
public class BaseResultEntity<T> {
    private static final int SUCCESS = 0;

    private int errorCode = SUCCESS;

    private String errorMsg = "";

    private String exceptionMsg = "";

    //显示数据（用户需要关心的数据）
    private T result;

    public boolean isSuccess() {
        return errorCode == SUCCESS;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getExceptionMsg() {
        return exceptionMsg;
    }

    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "BaseResultEntity{" +
                "errorCode=" + errorCode +
                ", errorMsg='" + errorMsg + '\'' +
                ", exceptionMsg='" + exceptionMsg + '\'' +
                ", result=" + result +
                '}';
    }
}
