package com.cmcc.arp.handler;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by zmcc on 17/4/10.
 * <p/>
 * 带时间窗的请求缓存策略:
 */
public class InMemoryWindowRequestStore extends InMemoryRequestStore {

    private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(100);

    // 2秒钟的重复请求时间窗
    @Override
    public void store(final String value) {
        executor.schedule(new Runnable() {
            public void run() {
                InMemoryWindowRequestStore.super.store(value);
            }
        }, 2, TimeUnit.SECONDS);
    }
}
