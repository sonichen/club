package com.cyj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyj.domain.Club;
import com.cyj.domain.Member;
import com.github.pagehelper.PageInfo;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * <p>
 * 社团成员表 服务类
 * </p>
 *
 * @author cyj
 * @since 2021-05-15
 */
public interface IMemberService extends IService<Member> {
    public PageInfo<Member> queryMemberAll(int page, int pageSize,Member member);

    int deleteByClubId(Integer clubId);
    Member  findByStudentId(String studentId);

    List<Club> findMyClub(String studentId);
    List<Club> findMyManagerClub(String studentId);
    /**
     * 查询社团成员表分页数据
     *
     * @param page      页码
     * @param pageCount 每页条数
     * @return IPage<Member>
     */
    IPage<Member> findListByPage(Integer page, Integer pageCount);

    /**
     * 添加社团成员表
     *
     * @param member 社团成员表
     * @return int
     */
    int add(Member member);

    /**
     * 删除社团成员表
     *
     * @param id 主键
     * @return int
     */
    int delete(Long id);

    /**
     * 修改社团成员表
     *
     * @param member 社团成员表
     * @return int
     */
    int updateData(Member member);

    /**
     * id查询数据
     *
     * @param id id
     * @return Member
     */
    Member findById(Long id);
}
