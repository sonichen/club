package com.cyj.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyj.domain.Club;
import com.cyj.domain.Member;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 社团信息表 Mapper 接口
 * </p>
 *
 * @author cyj
 * @since 2021-05-15
 */
@Component("clubDao")
public interface ClubMapper extends BaseMapper<Club> {
    List<Club> queryClubAll(Club club);

    int countClubMember(Club club);

    int updateCount(Club club);

    String findChief(Club club);

    int findIdByName(String name);

    List<Club> findByPerson(String studentId);
    int count(Integer status);
}
