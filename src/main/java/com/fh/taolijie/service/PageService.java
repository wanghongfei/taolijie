package com.fh.taolijie.service;

/**
 * Created by wanghongfei on 15-3-30.
 */
public interface PageService {
    <T extends PageViewAware> void increasePageView(Integer entityId, Class<T> type);
}
