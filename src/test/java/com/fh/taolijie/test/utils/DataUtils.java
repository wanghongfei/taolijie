package com.fh.taolijie.test.utils;

import com.fh.taolijie.dao.mapper.TestMapper;
import com.fh.taolijie.domain.SqlWrapper;
import com.fh.taolijie.utils.TimeUtil;

import java.util.*;

/**
 * Created by whf on 8/15/15.
 */
public class DataUtils {
    /**
     * 插入4个bruce用户
     * @param tm
     */
    public static Map<String, Integer> insertMemberData(TestMapper tm) {
        Map<String, Integer> idMap = new HashMap<>();

        Date now = new Date();
        // 现在的时间
        String nowTime = TimeUtil.date2String(now, "yyyy-MM-dd HH:mm:ss");
        // 现在时间之前1分钟
        Date _1minBefore = TimeUtil.calculateDate(now, Calendar.MINUTE, -1);
        String _1minBeforeStr = TimeUtil.date2String(_1minBefore, "yyyy-MM-dd HH:mm:ss");
        // 现在时间之前2分钟
        Date _2minBefore = TimeUtil.calculateDate(now, Calendar.MINUTE, -2);
        String _2minBeforeStr = TimeUtil.date2String(_1minBefore, "yyyy-MM-dd HH:mm:ss");

        String sql = "insert into member(username, password, last_job_date) values('bruce', '3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d','" + nowTime + "')";
        System.out.println("执行 sql: " + sql);
        SqlWrapper sw = new SqlWrapper(sql);
        tm.execute(sw);
        idMap.put("bruce", sw.getId());

        sql = "insert into member(username, password, last_job_date) values('bruce2', '3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d','" + _1minBeforeStr + "')";
        System.out.println("执行 sql: " + sql);
        sw = new SqlWrapper(sql);
        tm.execute(sw);
        idMap.put("bruce2", sw.getId());

        sql = "insert into member(username, password, last_job_date) values('bruce3', '3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d','" + _2minBeforeStr + "')";
        System.out.println("执行 sql: " + sql);
        sw = new SqlWrapper(sql);
        tm.execute(sw);
        idMap.put("bruce3", sw.getId());

        sql = "insert into member(username, password) values('bruce4', '3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d')";
        System.out.println("执行 sql: " + sql);
        sw = new SqlWrapper(sql);
        tm.execute(sw);
        idMap.put("bruce4", sw.getId());

        return idMap;
    }

    /**
     * 插入个人通知
     * @param tm
     */
    public static void insertPriNotificationData(TestMapper tm, Map<String, Integer> memIdMap) {
        String sql = null;
        Date now = new Date();
        // 现在的时间
        String nowTime = TimeUtil.date2String(now, "yyyy-MM-dd HH:mm:ss");
        // 现在时间之前1分钟
        Date _1minBefore = TimeUtil.calculateDate(now, Calendar.MINUTE, -1);
        String _1minBeforeStr = TimeUtil.date2String(_1minBefore, "yyyy-MM-dd HH:mm:ss");
        // 现在时间之前2分钟
        Date _2minBefore = TimeUtil.calculateDate(now, Calendar.MINUTE, -2);
        String _2minBeforeStr = TimeUtil.date2String(_1minBefore, "yyyy-MM-dd HH:mm:ss");


        sql = "insert into private_notification(member_id, title, content, is_read, access_range, to_member_id, time) values('" + memIdMap.get("bruce") + "', 'pri_no_1', 'content01', '0', 'PRIVATE', '" + memIdMap.get("bruce2") + "', '" + nowTime + "')";
        PrintUtils.print("执行 sql: " + sql);
        SqlWrapper sw = new SqlWrapper(sql);
        tm.execute(sw);

        sql = "insert into private_notification(member_id, title, content, is_read, access_range, to_member_id, time) values('" + memIdMap.get("bruce") + "', 'pri_no_2', 'content01', '1', 'PRIVATE', '" + memIdMap.get("bruce2") + "', '" + _2minBeforeStr + "')";
        PrintUtils.print("执行 sql: " + sql);
        sw = new SqlWrapper(sql);
        tm.execute(sw);

        sql = "insert into private_notification(member_id, title, content, is_read, access_range, to_member_id, time) values('" + memIdMap.get("bruce") + "', 'pri_no_3', 'content01', '1', 'PRIVATE', '" + memIdMap.get("bruce3") + "', '" + _2minBeforeStr + "')";
        PrintUtils.print("执行 sql: " + sql);
        sw = new SqlWrapper(sql);
        tm.execute(sw);

    }
}
