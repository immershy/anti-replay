package com.cmcc.arp.handler;

/**
 * Created by zmcc on 17/4/5.
 */
public interface RequestStore {

    void store(String value);

    Boolean contains(String value);
}
