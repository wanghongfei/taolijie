package com.fh.taolijie.service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

/**
 * Created by wanghongfei on 15-4-1.
 */
public interface SearchService {
    <T> List<T> runLikeQuery(Class<T> type, Map<String, Object> likeParam, Map.Entry<String, String> orderBy, EntityManager em);
    <T> List<T> runAccurateQuery(Class<T> type, Map<String, Object> likeParam, Map.Entry<String, String> orderBy, EntityManager em);
}
