package com.cmcc.arp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cmcc.arp.handler.ErrorHandler;
import com.cmcc.arp.handler.RequestStore;

/**
 * 防重放处理
 */
@Component
public class RequestPlayer {

    /**
     * 异常处理策略
     */
    @Autowired
    protected ErrorHandler errorHandler;

    @Autowired
    protected RequestStore requestStore;

    public RequestPlayer() {

    }

    /**
     * 记录请求参数
     *
     * @param value 请求参数封装
     */
    public void play(String value) {
        requestStore.store(value);
    }

    /**
     * 判断是否是重复请求
     *
     * @param value
     * @return
     */
    public boolean hasPlayed(String value) {
        return requestStore.contains(value);
    }

    /**
     * 异常处理
     *
     * @param param
     */
    public void handleError(Exception e, Object... param) {
        errorHandler.handleError(e, param);
    }

    /**
     * getter setter方法
     */
    public ErrorHandler getErrorHandler() {
        return errorHandler;
    }

    public void setErrorHandler(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    public RequestStore getRequestStore() {
        return requestStore;
    }

    public void setRequestStore(RequestStore requestStore) {
        this.requestStore = requestStore;
    }
}
