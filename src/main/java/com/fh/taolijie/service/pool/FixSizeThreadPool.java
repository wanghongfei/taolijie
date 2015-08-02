package com.fh.taolijie.service.pool;

import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 持有一个固定大小的线程池
 * Created by whf on 8/2/15.
 */
@Component
public class FixSizeThreadPool {
    public static final int DEFAULT_POOL_SIZE = 10;

    private ExecutorService pool;

    public FixSizeThreadPool() {
        pool = Executors.newFixedThreadPool(DEFAULT_POOL_SIZE);
    }

    public ExecutorService getPool() {
        return this.pool;
    }

    @PreDestroy
    public void shutdown() {
        pool.shutdownNow();
    }
}
