package com.cmcc.arp.handler;

import java.util.HashSet;

/**
 * Created by zmcc on 17/4/5.
 */
public class InMemoryRequestStore implements RequestStore {

    protected HashSet<String> set = new HashSet<String>(128);

    private long lastSaveTime = System.currentTimeMillis();

    // TODO 并发
    public void store(String value) {
        if ((System.currentTimeMillis() - lastSaveTime) > 12 * 60 * 60 * 1000) {
            lastSaveTime = System.currentTimeMillis();
            set = new HashSet<String>(128);
        }
        set.add(value);
    }

    public Boolean contains(String value) {
        return set.contains(value);
    }
}
