package com.cyj.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyj.domain.Club;
import com.cyj.domain.Member;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 社团成员表 Mapper 接口
 * </p>
 *
 * @author cyj
 * @since 2021-05-15
 */
@Component("memberDao")
public interface MemberMapper extends BaseMapper<Member> {
    List<Member> queryMemberAll(Member member);
    Member  findByStudentId(String studentId);

    List<Member> memberList(Member member);
//    int countClubMember(Club club);
    int deleteByClubId(Integer clubId);
//    List<Club> findByPerson(String id);

    List<Club> findMyClub(String studentId);
    List<Club> findMyManagerClub(String studentId);

    int countMemeber(Club club);

}
