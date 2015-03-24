package com.fh.taolijie.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * 所有与数据库测试相关的类都必须继承此基类
 * Created by wanghongfei on 15-3-5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { EmbededDatabaseBeanConfig.class })
@TransactionConfiguration
@Transactional(readOnly = true)
public class BaseDatabaseTestClass {
}
