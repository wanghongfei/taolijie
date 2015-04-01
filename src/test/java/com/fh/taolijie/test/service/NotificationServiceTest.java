package com.fh.taolijie.test.service;

import com.fh.taolijie.controller.dto.NotificationDto;
import com.fh.taolijie.domain.MemberEntity;
import com.fh.taolijie.domain.MemberRoleEntity;
import com.fh.taolijie.domain.NotificationEntity;
import com.fh.taolijie.domain.RoleEntity;
import com.fh.taolijie.service.NotificationService;
import com.fh.taolijie.service.impl.DefaultNotificationService;
import com.fh.taolijie.service.repository.MemberRepo;
import com.fh.taolijie.service.repository.NotificationRepo;
import com.fh.taolijie.test.service.repository.BaseSpringDataTestClass;
import com.fh.taolijie.utils.CollectionUtils;
import com.fh.taolijie.utils.Constants;
import com.fh.taolijie.utils.ObjWrapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    RoleEntity role;

    @Autowired
    NotificationRepo noRepo;
    @Autowired
    MemberRepo memberRepo;

    @PersistenceContext
    EntityManager em;

    @Autowired
    NotificationService noService;

    @Before
    public void initData() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        // 创建用户
        // password is 111111
        member = new MemberEntity("Bruce", "3d4f2bf07dc1be38b20cd6e46949a1071f9d0e3d", "", "Neo", "", "", "", "", "", "", 20, "", "", true, new Date());
        memberRepo.save(member);

        // 创建role
        role = new RoleEntity("STUDENT", "");
        em.persist(role);

        // 创建关联关系
        MemberRoleEntity memRole = new MemberRoleEntity();
        memRole.setRole(role);
        memRole.setMember(member);
        em.persist(memRole);

        no1 = new NotificationEntity();
        no1.setTitle("a note");
        no1.setIsRead(0);
        no1.setTime(sdf.parse("2000/1/1"));
        no1.setAccessRange(Constants.NotificationRange.GLOBAL.toString());
        no1.setMember(member);
        noRepo.save(no1);
        no2 = new NotificationEntity();
        no2.setTitle("some notes");
        no2.setIsRead(1);
        no2.setTime(sdf.parse("2015/1/1"));
        no2.setAccessRange(Constants.NotificationRange.PRIVATE.toString());
        no2.setMember(member);
        noRepo.save(no2);

        // create connection
        member.setNotificationCollection(CollectionUtils.addToCollection(member.getNotificationCollection(), no1));
        member.setNotificationCollection(CollectionUtils.addToCollection(member.getNotificationCollection(), no2));
    }

    @Test
    @Transactional(readOnly = true)
    public void getGetAmount() {
        Long tot = noService.getNotificationAmount(this.member.getId(), true);
        Assert.assertEquals(1, tot.intValue());
    }

    @Test
    @Transactional(readOnly = true)
    public void testGetList() throws Exception {
        List<NotificationDto> dtoList = noService.getNotificationList(member.getId(), role.getRolename(), 0, 0, new ObjWrapper());
        dtoList = noService.getNotificationList(member.getId(), role.getRolename(), true, 0, 0, new ObjWrapper());
        dtoList = noService.getNotificationList(member.getId(), role.getRolename(), false,  0, 0, new ObjWrapper());
        dtoList = noService.getNotificationList(member.getId(), role.getRolename(), new Date(),  0, 0, new ObjWrapper());

        // test get by member
        /*List<NotificationDto> dtoList = noService.getNotificationList(member.getId(), role.getRolename(), 0, 0, new ObjWrapper());
        Assert.assertNotNull(dtoList);
        Assert.assertFalse(dtoList.isEmpty());
        boolean contains = dtoList.stream()
                .anyMatch((dto) -> dto.getTitle().equals("a note"));
        Assert.assertTrue(contains);
        contains = dtoList.stream().anyMatch( (dto) -> dto.getTitle().equals("some notes") );
        Assert.assertTrue(contains);


        // test get by isRead
        dtoList = noService.getNotificationList(member.getId(), role.getRolename(), true, 0, 0, new ObjWrapper());
        contains = dtoList.stream().anyMatch((dto) -> dto.getTitle().equals("a note"));
        Assert.assertTrue(contains);
        contains = dtoList.stream().anyMatch( (dto) -> dto.getTitle().equals("some notes") );
        Assert.assertTrue(contains);

        // test get by isRead
        dtoList = noService.getNotificationList(member.getId(), role.getRolename(), false,  0, 0, new ObjWrapper());
        contains = dtoList.stream().anyMatch( (dto) -> dto.getTitle().equals("a note") );
        Assert.assertTrue(contains);



        // test get by time
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        dtoList = noService.getNotificationList(member.getId(), role.getRolename(), sdf.parse("2005/1/1"),  0, 0, new ObjWrapper());
        contains = dtoList.stream().anyMatch((dto) -> dto.getTitle().equals("some notes"));
        Assert.assertTrue(contains);
        contains = dtoList.stream().noneMatch( (dto) -> dto.getTime().equals("a note") );
        Assert.assertTrue(contains);*/

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
