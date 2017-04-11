package com.cmcc.arp.handler;

import java.util.HashSet;

/**
 * Created by zmcc on 17/4/5.
 */
public class InMemoryRequestStore implements RequestStore {

    protected HashSet<String> set = new HashSet<String>(128);

    protected long lastSaveTime = System.currentTimeMillis();

    public static final int MAX_SIZE = 100000;

    public static final long MAX_STORE_TIME = 12 * 60 * 60 * 1000;

    // TODO 并发
    public void store(String value) {
        if (tooLong(lastSaveTime) || tooLarge(set)) {
            lastSaveTime = System.currentTimeMillis();
            set = new HashSet<String>(128);
        }
        set.add(value);
    }

    public Boolean contains(String value) {
        return set.contains(value);
    }

    private Boolean tooLong(long time) {
        return (System.currentTimeMillis() - time) > MAX_STORE_TIME;
    }

    private Boolean tooLarge(HashSet<String> set) {
        return set != null && set.size() > MAX_SIZE;
    }
}
