package com.cyj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyj.domain.Activity;
import com.cyj.domain.Club;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cyj
 * @since 2021-05-17
 */
public interface IActivityService extends IService<Activity> {
    int count(Integer status);
    int deleteByClubId(Integer clubId);
    public PageInfo<Activity> queryActivityAll(int page, int pageSize,Activity activity);

    /**
     * 查询分页数据
     *
     * @param page      页码
     * @param pageCount 每页条数
     * @return IPage<Activity>
     */
    IPage<Activity> findListByPage(Integer page, Integer pageCount);

    /**
     * 添加
     *
     * @param activity 
     * @return int
     */
    int add(Activity activity);

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
     * @param activity 
     * @return int
     */
    int updateData(Activity activity);

    /**
     * id查询数据
     *
     * @param id id
     * @return Activity
     */
    Activity findById(Long id);
}
