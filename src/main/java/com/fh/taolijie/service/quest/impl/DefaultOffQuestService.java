package com.fh.taolijie.service.quest.impl;

import com.alibaba.fastjson.JSON;
import com.fh.taolijie.component.ListResult;
import com.fh.taolijie.component.http.HttpClientFactory;
import com.fh.taolijie.constant.RedisKey;
import com.fh.taolijie.constant.quest.OffQuestStatus;
import com.fh.taolijie.dao.mapper.MemberModelMapper;
import com.fh.taolijie.dao.mapper.OffCateModelMapper;
import com.fh.taolijie.dao.mapper.OffQuestModelMapper;
import com.fh.taolijie.domain.OffCateModel;
import com.fh.taolijie.domain.OffQuestModel;
import com.fh.taolijie.dto.map.BaiduMapDto;
import com.fh.taolijie.dto.map.BaiduMapNearbyRespDto;
import com.fh.taolijie.exception.checked.GeneralCheckedException;
import com.fh.taolijie.exception.checked.HTTPConnectionException;
import com.fh.taolijie.exception.checked.PermissionException;
import com.fh.taolijie.exception.checked.PostIntervalException;
import com.fh.taolijie.exception.checked.certi.IdUnverifiedException;
import com.fh.taolijie.exception.checked.quest.QuestNotFoundException;
import com.fh.taolijie.service.certi.IdCertiService;
import com.fh.taolijie.service.channel.BaiduMapService;
import com.fh.taolijie.service.impl.IntervalCheckService;
import com.fh.taolijie.service.quest.OffQuestService;
import com.fh.taolijie.utils.JedisUtils;
import com.fh.taolijie.utils.LogUtils;
import com.fh.taolijie.utils.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMethod;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Created by whf on 11/30/15.
 * @author wanghongfei
 */
@Service
public class DefaultOffQuestService implements OffQuestService {
    private static Logger logger = LoggerFactory.getLogger(DefaultOffQuestService.class);

    @Autowired
    private OffQuestModelMapper questMapper;

    @Autowired
    private IdCertiService idService;

    @Autowired
    private IntervalCheckService intervalService;

    @Autowired
    private MemberModelMapper memMapper;

    @Autowired
    private OffCateModelMapper cateMapper;

    @Autowired
    private BaiduMapService mapService;

    @Autowired
    private JedisPool jedisPool;


    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void publish(OffQuestModel model) throws GeneralCheckedException {
        Integer memId = model.getMemId();

        // 发布时间间隔检查
        if (false == intervalService.checkOffQuestPostInterval(memId)) {
            throw new PostIntervalException();
        }

        // 检查用户是否完成了id认证
        boolean verified = idService.checkVerified(memId);
        if (false == verified) {
            throw new IdUnverifiedException();
        }


        // 执行发布
        Date now = new Date();
        model.setCreatedTime(now);
        // 设置username冗余字段
        String username = memMapper.selectUsername(memId);
        model.setUsername(username);
        // 设置cate_name冗余字段
        OffCateModel cate = cateMapper.selectByPrimaryKey(model.getCateId());
        model.setCateName(cate.getName());
        // 设置状态为已发布
        model.setStatus(OffQuestStatus.PUBLISHED.code());
        model.setFlushTime(now);

        questMapper.insertSelective(model);

        // 向百度地图加点
        sendPosition(model.getLongitude(), model.getLatitude(), model.getId(), model.getId().toString());
    }

    /**
     * 将任务经纬度发送到百度地图
     */
    private boolean sendPosition(BigDecimal longitude, BigDecimal latitude, Integer questId, String title) throws GeneralCheckedException {
        // 从redis中取出连接参数
        Map<String, String> map = JedisUtils.performOnce(jedisPool, jedis -> jedis.hgetAll(RedisKey.MAP_CONF.toString()) );
        String AK = map.get(RedisKey.MAP_AK.toString());
        String coord = map.get(RedisKey.MAP_COORD_TYPE.toString());
        String geotableId = map.get(RedisKey.MAP_GEOTABLE_ID.toString());
        String addr = map.get(RedisKey.MAP_CREATE_PT.toString());

        logger.debug("ak = {}, coord = {}, geotableId = {}, addr = {}", AK, coord, geotableId, addr);

        Map<String, String> parmMap = new HashMap<>(13);
        parmMap.put("ak", AK);
        parmMap.put("coord_type", coord);
        parmMap.put("geotable_id", geotableId);
        parmMap.put("longitude", longitude.toString() );
        parmMap.put("latitude", latitude.toString() );
        parmMap.put("quest_id", questId.toString());
        parmMap.put("title", title);

        // 发送请求
        BaiduMapDto dto = mapService.sendRequest(addr, parmMap, RequestMethod.POST, BaiduMapDto.class);

        // 判断是否成功
        if (false == "0".equals(dto.status)) {
            // 失败
            LogUtils.getErrorLogger().error("Position sending failed");
            return false;
        }


        return true;

    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<OffQuestModel> findBy(OffQuestModel cmd) {
        List<OffQuestModel> list = questMapper.findBy(cmd);
        long tot = questMapper.countFindBy(cmd);

        return new ListResult<>(list, tot);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void updateStatus(Integer questId, Integer memId, OffQuestStatus status) throws GeneralCheckedException {
        checkOwner(questId, memId);

        // 修改状态
        OffQuestModel cmd = new OffQuestModel();
        cmd.setId(questId);
        cmd.setStatus(status.code());
        questMapper.updateByPrimaryKeySelective(cmd);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Throwable.class)
    public void flush(Integer questId, Integer memId) throws GeneralCheckedException {
        checkOwner(questId, memId);

        // 修改状态
        OffQuestModel cmd = new OffQuestModel();
        cmd.setId(questId);
        cmd.setFlushTime(new Date());
        questMapper.updateByPrimaryKeySelective(cmd);

    }

    private void checkOwner(Integer questId, Integer memId) throws GeneralCheckedException {
        OffQuestModel quest = questMapper.selectByPrimaryKey(questId);
        // 验证任务存在性
        if (null == quest) {
            throw new QuestNotFoundException(questId.toString());
        }

        // 验证任务所有者是不是memId
        if (false == quest.getMemId().equals(memId)) {
            throw new PermissionException();
        }

    }

    @Override
    @Transactional(readOnly = true)
    public ListResult<OffQuestModel> nearbyQuest(BigDecimal longitude, BigDecimal latitude, int radis, int pn, int ps) throws GeneralCheckedException {
        // 向地图查询附近的坐标点
        Map<Integer, Integer> questDistMap = queryNearbyQuest(longitude, latitude, radis, pn, ps);
        if (null == questDistMap || questDistMap.isEmpty()) {
            return new ListResult<>();
        }

        // 根据任务id查询数据库
        List<Integer> idList = questDistMap.keySet().stream()
                .collect(Collectors.toList());
        List<OffQuestModel> list = questMapper.selectInBatch(idList);

        // 为结果集中的distance赋值
        list.forEach( quest -> {
            Integer dist = questDistMap.get(quest.getId());
            quest.setDistance(dist);
        });

        return new ListResult<>(list, list.size());
    }

    @Override
    public int distance(double start, double end) {
        return 0;
    }

    /**
     * 向百度地图发起范围查询
     * @return key: 任务id, val: 距离
     */
    private Map<Integer, Integer> queryNearbyQuest(BigDecimal longitude, BigDecimal latitude, int range, int pn, int ps) throws GeneralCheckedException {
        // 从redis中取出连接参数
        Map<String, String> map = JedisUtils.performOnce(jedisPool, jedis -> jedis.hgetAll(RedisKey.MAP_CONF.toString()) );
        String AK = map.get(RedisKey.MAP_AK.toString());
        String geotableId = map.get(RedisKey.MAP_GEOTABLE_ID.toString());
        String addr = map.get(RedisKey.MAP_RANGE_QUERY.toString());

        logger.debug("ak = {}, coord = {}, addr = {}", AK, geotableId, addr);

        // 拼接GET请求
        Map<String, String> paramMap = new HashMap<>(10);
        paramMap.put("ak", AK);
        paramMap.put("geotable_id", geotableId);
        paramMap.put("radis", String.valueOf(range));
        paramMap.put("sortBy", "distance");
        paramMap.put("page_index", String.valueOf(pn) );
        paramMap.put("page_size", String.valueOf(ps) );
        String location = StringUtils.concat(60, longitude.toString(), ",", latitude.toString());
        paramMap.put("location", location);

        // 发送请求
        BaiduMapNearbyRespDto dto = mapService.sendRequest(addr, paramMap, RequestMethod.GET, BaiduMapNearbyRespDto.class);
        // 判断是否成功
        if (false == "0".equals(dto.status)) {
            // 失败
            LogUtils.getErrorLogger().error("nearby search failed");
            throw new HTTPConnectionException();
        }

        return Stream.of(dto.contents)
                .collect(Collectors.toMap(con -> con.quest_id, con -> con.distance) );
    }
}
