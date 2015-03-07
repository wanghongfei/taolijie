package com.fh.taolijie.service.repository;

import com.fh.taolijie.domain.NewsEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by wanghongfei on 15-3-8.
 */
public interface NewsRepo extends CrudRepository<NewsEntity, Integer> {
}
