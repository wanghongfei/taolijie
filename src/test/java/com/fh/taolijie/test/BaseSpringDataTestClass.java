package com.fh.taolijie.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by wanghongfei on 15-4-10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MySqlConfigBean.class })
@TransactionConfiguration
@Transactional(readOnly = false)
public class BaseSpringDataTestClass {
}
