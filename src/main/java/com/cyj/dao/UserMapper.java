package com.cyj.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyj.domain.User;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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
@Component("userDao")
public interface UserMapper extends BaseMapper<User> {
    List<User> queryUserAll(User userinfo);

    User queryUserByNameAndPwd(User userinfo);

    User queryByAccount(User user);

    int findIdByNumberAndType(User user);

    List<User> findAll(User user);

    User queryUserByEmailAndPwd(User userinfo);

}
