package com.fh.taolijie.component.http;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用了连接池的HttpClient工厂
 * Created by whf on 10/7/15.
 */
public class HttpClientFactory {
    private static Logger infoLog = LoggerFactory.getLogger("info");

    private static PoolingHttpClientConnectionManager pool;

    private static HttpClientBuilder builder;

    public static int MAX_PER_ROUTE = 20;
    public static int MAX_TOTAL = 50;


    static {
        infoLog.info("creating HTTP connection pool: max-per-route:{}, max-total:{}", MAX_PER_ROUTE, MAX_TOTAL);

        pool = new PoolingHttpClientConnectionManager();
        pool.setDefaultMaxPerRoute(MAX_PER_ROUTE);
        pool.setMaxTotal(MAX_TOTAL);

        builder = HttpClientBuilder.create();
        builder.setConnectionManager(pool);

        infoLog.info("done creating HTTP connection pool");
    }

    public static HttpClient getClient() {
        return builder.build();
    }
}
