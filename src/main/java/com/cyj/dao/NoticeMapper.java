package com.cyj.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyj.domain.Club;
import com.cyj.domain.Notice;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cyj
 * @since 2021-05-15
 */
@Component("noticeDao")
public interface NoticeMapper extends BaseMapper<Notice> {
    List<Notice> queryNoticeAll(Notice notice);
    int deleteByClubId(Integer clubId);
}
