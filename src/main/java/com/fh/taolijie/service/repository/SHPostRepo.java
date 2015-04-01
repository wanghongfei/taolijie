package com.fh.taolijie.service.repository;

import com.fh.taolijie.domain.MemberEntity;
import com.fh.taolijie.domain.SecondHandPostCategoryEntity;
import com.fh.taolijie.domain.SecondHandPostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;


/**
 * Created by wanghongfei on 15-3-8.
 */
public interface SHPostRepo extends JpaRepository<SecondHandPostEntity, Integer> {
    @Query("SELECT post FROM SecondHandPostEntity post WHERE post.category = ?1 ORDER BY post.postTime DESC ")
    Page<SecondHandPostEntity> findByCategory(SecondHandPostCategoryEntity category, Pageable pageable);

    /**
     * 得到未过期的用户帖子
     * @return
     */
    @Query("SELECT post FROM SecondHandPostEntity post WHERE post.member = ?1 AND post.expiredTime > ?2")
    Page<SecondHandPostEntity> findByMemberAndNotExpired(MemberEntity member, Date nowTime, Pageable pageable);

    /**
     * 得到某个用户的所有帖子
     * @return
     */
    @Query("SELECT post FROM SecondHandPostEntity post WHERE post.member = ?1")
    Page<SecondHandPostEntity> findByMember(MemberEntity member, Pageable pageable);

    Page<SecondHandPostEntity> findByVerified(String verified, Pageable pageable);
}
