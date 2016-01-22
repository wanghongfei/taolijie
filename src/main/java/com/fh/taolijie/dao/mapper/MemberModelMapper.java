package com.fh.taolijie.dao.mapper;

import com.fh.taolijie.dto.CreditsInfo;
import com.fh.taolijie.domain.acc.MemberModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MemberModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MemberModel record);

    int insertSelective(MemberModel record);

    MemberModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MemberModel record);


    int updateByPrimaryKey(MemberModel record);

    List<MemberModel> findBy(MemberModel example);
    long countFindBy(MemberModel example);

    MemberModel selectByUsername(String username);

    /**
     * 根据用户名模糊查询用户
     * @param username
     * @return
     */
    List<MemberModel> searchMember(@Param("username") String username, @Param("pn") int pn, @Param("ps") int ps);
    long countSearchMember(String username);


    MemberModel selectByAppToken(String token);
    void updateAppToken(@Param("memId") Integer memId, @Param("token") String token);

    String selectUsername(Integer memId);

    Long getMemberAmount();

    boolean checkUserExist(String username);

    MemberModel selectByIdentifier(String identifier);

    MemberModel selectByWechatToken(String token);

    List<MemberModel> getMemberList(Map<String, Integer> pageMap);
    long countGetMemberList();

    CreditsInfo queryCreditsInfo(Integer memberId);

    void validMemberById(@Param("memberId") Integer memberId, @Param("valid") boolean valid);

    MemberModel findByEmail(String email);

    void addCredits(@Param("memId") Integer memId, @Param("value") int value, @Param("newLevel") String newLevel);

    /**
     * 只返回商家认证状态字段
     * @param memId
     * @return
     */
    String selectEmpVerified(Integer memId);

    /**
     * 只返回学生认证状态字段
     * @param memId
     * @return
     */
    String selectStuVerified(Integer memId);

    /**
     * 只返回ID认证字段
     * @param memId
     * @return
     */
    String selectIdVerified(Integer memId);
}