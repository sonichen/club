package com.cyj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyj.domain.Club;
import com.cyj.domain.Clubtype;
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
public interface IClubtypeService extends IService<Clubtype> {
    public PageInfo<Clubtype> queryClubtypeAll(int page, int pageSize,Clubtype clubtype);

    List<Clubtype> findAll();
    List<Clubtype> findAllClubtype(Clubtype clubtype);

    /**
     * 查询分页数据
     *
     * @param page      页码
     * @param pageCount 每页条数
     * @return IPage<Clubtype>
     */
    IPage<Clubtype> findListByPage(Integer page, Integer pageCount);

    /**
     * 添加
     *
     * @param clubtype 
     * @return int
     */
    int add(Clubtype clubtype);

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
     * @param clubtype 
     * @return int
     */
    int updateData(Clubtype clubtype);

    /**
     * id查询数据
     *
     * @param id id
     * @return Clubtype
     */
    Clubtype findById(Long id);
}
