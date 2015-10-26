package com.fh.taolijie.service;

import java.util.Map;

/**
 * Created by whf on 7/7/15.
 */
public interface StatisticsService {
    /**
     * 查询页面访问量统计信息
     * @return
     */
    Map<String, String> getPageViewStatistics();
}
