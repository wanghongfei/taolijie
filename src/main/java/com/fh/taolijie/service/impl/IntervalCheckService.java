package com.fh.taolijie.service.impl;

import com.fh.taolijie.service.AccountService;
import com.fh.taolijie.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by whf on 8/15/15.
 */
@Service
public class IntervalCheckService {
    @Autowired
    private AccountService accountService;

    public final boolean checkInterval(Integer memId, Date lastDate, int interval, TimeUnit unit) {
        Date nowTime = new Date();
        if (null == lastDate) {
            return true;
        }

        boolean result = doCheck(lastDate, nowTime, interval, unit);
        if (false == result) {
            return false;
        }

        return true;
    }


    private boolean doCheck(Date lastJobTime, Date nowTime, int value, TimeUnit unit) {
        // 检查上次发布的时间间隔
        // 如果时间为空，说明这是用户第一次发帖
        if (null != lastJobTime) {
            boolean enoughInterval = TimeUtil.intervalGreaterThan(nowTime, lastJobTime, value, unit);

            // 时间间隔少于1min
            if (false == enoughInterval) {
                return false;
            }
        }

        return true;
    }
}
