package com.cyj.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyj.domain.Club;
import com.cyj.domain.Finance;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 财务项目表 Mapper 接口
 * </p>
 *
 * @author cyj
 * @since 2021-05-15
 */
@Component("financeDao")
public interface FinanceMapper extends BaseMapper<Finance> {

    List<Finance> queryFinanceAll(Finance finance);
    int deleteByClubId(Integer clubId);
}
