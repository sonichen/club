package com.cyj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyj.domain.Club;
import com.cyj.domain.Clubtype;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 社团信息表 服务类
 * </p>
 *
 * @author cyj
 * @since 2021-05-15
 */
public interface IClubService extends IService<Club> {
    int countClubMember(Club club);
    public PageInfo<Club> queryClubAll(int page, int pageSize,Club club);
    List<Club> findAll();
int updateCount(Club club);
    String findChief(Club club);
   int findIdByName(String name);
    int count(Integer status);
    int countMemeber(Club club);

    /**
     * 查询社团信息表分页数据
     *
     * @param page      页码
     * @param pageCount 每页条数
     * @return IPage<Club>
     */
    IPage<Club> findListByPage(Integer page, Integer pageCount);

    /**
     * 添加社团信息表
     *
     * @param club 社团信息表
     * @return int
     */
    int add(Club club);

    /**
     * 删除社团信息表
     *
     * @param id 主键
     * @return int
     */
    int delete(Long id);

    /**
     * 修改社团信息表
     *
     * @param club 社团信息表
     * @return int
     */
    int updateData(Club club);

    /**
     * id查询数据
     *
     * @param id id
     * @return Club
     */
    Club findById(Long id);
}
