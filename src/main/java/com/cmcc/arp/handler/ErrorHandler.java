package com.cmcc.arp.handler;

/**
 * Created by zmcc on 17/4/1.
 */
public interface ErrorHandler {

    void handleError(Exception e, Object... param);
}
