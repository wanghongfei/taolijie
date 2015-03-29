package com.fh.taolijie.test.service;

import com.fh.taolijie.controller.dto.NotificationDto;
import com.fh.taolijie.domain.MemberEntity;
import com.fh.taolijie.domain.NotificationEntity;
import com.fh.taolijie.service.NotificationService;
import com.fh.taolijie.service.impl.DefaultNotificationService;
import com.fh.taolijie.service.repository.MemberRepo;
import com.fh.taolijie.service.repository.NotificationRepo;
import com.fh.taolijie.test.service.repository.BaseSpringDataTestClass;
import com.fh.taolijie.utils.CollectionUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by wanghongfei on 15-3-9.
 */
@ContextConfiguration(classes = {
        DefaultNotificationService.class
})
public class NotificationServiceTest extends BaseSpringDataTestClass {
    MemberEntity member;
    NotificationEntity no1;
    NotificationEntity no2;

    @Autowired
    NotificationRepo noRepo;
    @Autowired
    MemberRepo memberRepo;

    @Autowired
    NotificationService noService;

    @Before
    public void initData() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        // 创建用户
        // password is 111111
        member = new MemberEntity("Bruce", "3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d", "", "Neo", "", "", "", "", "", "", 20, "", "");
        memberRepo.save(member);

        no1 = new NotificationEntity();
        no1.setTitle("a note");
        no1.setIsRead(0);
        no1.setTime(sdf.parse("2000/1/1"));
        no1.setMember(member);
        noRepo.save(no1);
        no2 = new NotificationEntity();
        no2.setTitle("some notes");
        no2.setIsRead(1);
        no2.setTime(sdf.parse("2015/1/1"));
        no2.setMember(member);
        noRepo.save(no2);

        // create connection
        member.setNotificationCollection(CollectionUtils.addToCollection(member.getNotificationCollection(), no1));
        member.setNotificationCollection(CollectionUtils.addToCollection(member.getNotificationCollection(), no2));
    }

    @Test
    @Transactional(readOnly = true)
    public void testGetList() throws Exception {
        // test get by member
        List<NotificationDto> dtoList = noService.getNotificationList(member.getId(), 0, 0);
        Assert.assertNotNull(dtoList);
        Assert.assertFalse(dtoList.isEmpty());
        boolean contains = dtoList.stream()
                .anyMatch((dto) -> dto.getTitle().equals("a note"));
        Assert.assertTrue(contains);
        contains = dtoList.stream().anyMatch( (dto) -> dto.getTitle().equals("some notes") );
        Assert.assertTrue(contains);


        // test get by isRead
        dtoList = noService.getNotificationList(member.getId(), true,  0, 0);
        contains = dtoList.stream().anyMatch( (dto) -> dto.getTitle().equals("some notes") );
        Assert.assertTrue(contains);

        // test get by isRead
        dtoList = noService.getNotificationList(member.getId(), false,  0, 0);
        contains = dtoList.stream().anyMatch( (dto) -> dto.getTitle().equals("a note") );
        Assert.assertTrue(contains);



        // test get by time
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        dtoList = noService.getNotificationList(member.getId(), sdf.parse("2005/1/1"),  0, 0);
        contains = dtoList.stream().anyMatch((dto) -> dto.getTitle().equals("some notes"));
        Assert.assertTrue(contains);
        contains = dtoList.stream().noneMatch( (dto) -> dto.getTime().equals("a note") );
        Assert.assertTrue(contains);

    }

    @Test
    @Transactional(readOnly = false)
    public void testDelete() {
        noService.deleteNotification(no1.getId());

        Assert.assertNull(noRepo.findOne(no1.getId()));
    }

    @Test
    @Transactional(readOnly = false)
    public void testMarkAsRead() {
        noService.markAsRead(no1.getId());

        Assert.assertEquals(1, noRepo.findOne(no1.getId()).getIsRead().intValue());
    }

    @Test
    @Transactional(readOnly = false)
    public void testAdd() {
        NotificationDto dto = new NotificationDto();
        dto.setTitle("another note");
        dto.setMemberId(member.getId());

        noService.addNotification(dto);
        boolean contains = memberRepo.findOne(member.getId()).getNotificationCollection()
                .stream()
                .anyMatch( (no) -> no.getTitle().equals("another note") );

        Assert.assertTrue(contains);
    }
}
