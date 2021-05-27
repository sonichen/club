package com.cyj.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyj.dao.ClubMapper;
import com.cyj.dao.UserMapper;
import com.cyj.domain.User;
import com.cyj.service.IUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cyj
 * @since 2021-05-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userDao;

    @Override
    public int findIdByNumberAndType(User user) {
        return userDao.findIdByNumberAndType(user);
    }

    @Override
    public PageInfo<User> findUserAll(int page, int pageSize, User userinfo) {
        PageHelper.startPage(page,pageSize);
        //查询的结果集
        List<User> list=userDao.queryUserAll(userinfo);
        PageInfo<User> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public User queryUserByNameAndPwd(User userinfo) {
        return  userDao.queryUserByNameAndPwd(userinfo);
    }

    @Override
    public User queryUserByEmailAndPwd(User user) {
        return  userDao.queryUserByEmailAndPwd(user);
    }

    @Override
    public User queryByAccount(User user) {
        return userDao.queryByAccount(user);
    }

    @Override
    public List<User> findAll(User user) {
        return  userDao.findAll(user);
    }

    @Override
    public IPage<User> findListByPage(Integer page, Integer pageCount) {
        return null;
    }

//    @Override
//    public  IPage<User> findListByPage(Integer page, Integer pageCount){
//        IPage<User> wherePage = new Page<>(page, pageCount);
//        User where = new User();
//
//        return   baseMapper.selectPage(wherePage, Wrappers.query(where));
//    }

    @Override
    public int add(User user){
        return baseMapper.insert(user);
    }

    @Override
    public int delete(Long id){
        return baseMapper.deleteById(id);
    }

    @Override
    public int updateData(User user){
        return baseMapper.updateById(user);
    }

    @Override
    public User findById(Long id){
        return  baseMapper.selectById(id);
    }
}
