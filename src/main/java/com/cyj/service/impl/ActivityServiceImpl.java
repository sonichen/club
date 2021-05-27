package com.cyj.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyj.dao.ActivityMapper;
import com.cyj.dao.ClubMapper;
import com.cyj.domain.Activity;
import com.cyj.domain.Club;
import com.cyj.service.IActivityService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cyj
 * @since 2021-05-17
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements IActivityService {
    @Autowired
    private ActivityMapper activityMapper;

    @Override
    public int count(Integer status) {
        return activityMapper.count(status);
    }

    @Override
    public int deleteByClubId(Integer clubId) {
        return activityMapper.deleteByClubId(clubId);
    }

    @Override
    public PageInfo<Activity> queryActivityAll(int page, int pageSize,Activity activity) {
        PageHelper.startPage(page, pageSize);
//        查询的结果集
        List<Activity> list = activityMapper.queryActivityAll(activity);
        PageInfo<Activity> pageInfo = new PageInfo<>(list);

        return pageInfo;
    }

    @Override
    public  IPage<Activity> findListByPage(Integer page, Integer pageCount){
        IPage<Activity> wherePage = new Page<>(page, pageCount);
        Activity where = new Activity();

        return   baseMapper.selectPage(wherePage, Wrappers.query(where));
    }

    @Override
    public int add(Activity activity){
        return baseMapper.insert(activity);
    }

    @Override
    public int delete(Long id){
        return baseMapper.deleteById(id);
    }

    @Override
    public int updateData(Activity activity){
        return baseMapper.updateById(activity);
    }

    @Override
    public Activity findById(Long id){
        return  baseMapper.selectById(id);
    }
}
