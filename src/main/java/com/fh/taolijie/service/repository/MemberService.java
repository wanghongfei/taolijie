package com.fh.taolijie.service.repository;

import com.fh.taolijie.domain.MemberEntity;

/**
 * Created by wanghongfei on 15-3-8.
 */
public interface MemberService {
    MemberEntity findMember(Integer memId);
}
