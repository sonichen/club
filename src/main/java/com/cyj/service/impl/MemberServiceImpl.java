package com.cyj.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyj.dao.MemberMapper;
import com.cyj.domain.Club;
import com.cyj.domain.Member;
import com.cyj.domain.Notice;
import com.cyj.service.IClubService;
import com.cyj.service.IMemberService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 社团成员表 服务实现类
 * </p>
 *
 * @author cyj
 * @since 2021-05-15
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements IMemberService {

    @Autowired
    private MemberMapper memberDao;



    @Override
    public PageInfo<Member> queryMemberAll(int page, int pageSize,Member member) {
        PageHelper.startPage(page, pageSize);
//        查询的结果集
        List<Member> list = memberDao.queryMemberAll(member);
        PageInfo<Member> pageInfo = new PageInfo<>(list);

        return pageInfo;
    }

    @Override
    public int deleteByClubId(Integer clubId) {
        return memberDao.deleteByClubId(clubId) ;
    }

    @Override
    public Member findByStudentId(String studentId) {
        return memberDao.findByStudentId(studentId);
    }

    @Autowired
    private IClubService clubDao;
    @Override
    public List<Club> findMyClub(String studentId) {
        return memberDao.findMyClub(studentId);
    }

    @Override
    public List<Club> findMyManagerClub(String studentId) {
        return memberDao.findMyManagerClub(studentId);
    }


    @Override
    public  IPage<Member> findListByPage(Integer page, Integer pageCount){
        IPage<Member> wherePage = new Page<>(page, pageCount);
        Member where = new Member();

        return   baseMapper.selectPage(wherePage, Wrappers.query(where));
    }

    @Override
    public int add(Member member){
        return baseMapper.insert(member);
    }

    @Override
    public int delete(Long id){
        return baseMapper.deleteById(id);
    }

    @Override
    public int updateData(Member member){
        return baseMapper.updateById(member);
    }

    @Override
    public Member findById(Long id){
        return  baseMapper.selectById(id);
    }
}
