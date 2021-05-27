package com.cyj.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyj.domain.Activity;
import com.cyj.domain.Club;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cyj
 * @since 2021-05-17
 */
@Component("activityDao")
public interface ActivityMapper extends BaseMapper<Activity> {

    List<Activity> queryActivityAll(Activity activity);

    int deleteByClubId(Integer clubId);
    int count(Integer status);
}
