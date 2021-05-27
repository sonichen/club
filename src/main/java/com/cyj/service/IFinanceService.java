package com.cyj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyj.domain.Club;
import com.cyj.domain.Finance;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 财务项目表 服务类
 * </p>
 *
 * @author cyj
 * @since 2021-05-15
 */
public interface IFinanceService extends IService<Finance> {
    int deleteByClubId(Integer clubId);
    public PageInfo<Finance> queryFinanceAll(int page, int pageSize,Finance finance);
    /**
     * 查询财务项目表分页数据
     *
     * @param page      页码
     * @param pageCount 每页条数
     * @return IPage<Finance>
     */
    IPage<Finance> findListByPage(Integer page, Integer pageCount);

    /**
     * 添加财务项目表
     *
     * @param finance 财务项目表
     * @return int
     */
    int add(Finance finance);

    /**
     * 删除财务项目表
     *
     * @param id 主键
     * @return int
     */
    int delete(Long id);

    /**
     * 修改财务项目表
     *
     * @param finance 财务项目表
     * @return int
     */
    int updateData(Finance finance);

    /**
     * id查询数据
     *
     * @param id id
     * @return Finance
     */
    Finance findById(Long id);
}
