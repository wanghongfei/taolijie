package com.fh.taolijie.test.service.repository;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by wanghongfei on 15-3-8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringDataConfigBean.class })
@TransactionConfiguration
@Transactional(readOnly = true)
public class BaseSpringDataTestClass {
}
