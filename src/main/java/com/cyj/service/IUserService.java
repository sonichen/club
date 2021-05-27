package com.cyj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyj.domain.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cyj
 * @since 2021-05-15
 */
public interface IUserService extends IService<User> {

    int findIdByNumberAndType(User user);
    PageInfo<User> findUserAll(int page, int pageSize, User userinfo);
    User queryUserByNameAndPwd(User userinfo);
    User queryUserByEmailAndPwd(User user);
    User queryByAccount(User user);
    List<User> findAll(User user);
    /**
     * 查询分页数据
     *
     * @param page      页码
     * @param pageCount 每页条数
     * @return IPage<User>
     */
    IPage<User> findListByPage(Integer page, Integer pageCount);

    /**
     * 添加
     *
     * @param user 
     * @return int
     */
    int add(User user);

    /**
     * 删除
     *
     * @param id 主键
     * @return int
     */
    int delete(Long id);

    /**
     * 修改
     *
     * @param user 
     * @return int
     */
    int updateData(User user);

    /**
     * id查询数据
     *
     * @param id id
     * @return User
     */
    User findById(Long id);
}
