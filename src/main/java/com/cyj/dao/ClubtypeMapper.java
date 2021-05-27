package com.cyj.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyj.domain.Club;
import com.cyj.domain.Clubtype;
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
@Component("clubtypeDao")
public interface ClubtypeMapper extends BaseMapper<Clubtype> {
    List<Clubtype> queryClubtypeAll(Clubtype clubtype);

    List<Clubtype> findAllClubtype(Clubtype clubtype);


}
