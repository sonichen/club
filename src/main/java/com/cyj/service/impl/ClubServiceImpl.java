package com.cyj.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyj.dao.ClubMapper;
import com.cyj.dao.MemberMapper;
import com.cyj.domain.Club;
import com.cyj.service.IClubService;
import com.cyj.util.BeanMapUtils;
import com.cyj.util.MapParameter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 社团信息表 服务实现类
 * </p>
 *
 * @author cyj
 * @since 2021-05-15
 */
@Service
public class ClubServiceImpl extends ServiceImpl<ClubMapper, Club> implements IClubService {
    @Autowired
    private ClubMapper clubDao;

private MemberMapper memberDao;
    @Override
    public int countClubMember(Club club) {
        return  clubDao.countClubMember(club);
    }

    public PageInfo<Club> queryClubAll(int page, int pageSize, Club club) {
        PageHelper.startPage(page, pageSize);
//        查询的结果集
        List<Club> list = clubDao.queryClubAll(club);
        System.out.println("结果集=="+list);
        PageInfo<Club> pageInfo = new PageInfo<>(list);

        return pageInfo;
    }

    @Override
    public List<Club> findAll() {
        return baseMapper.selectList(null);
    }

    @Override
    public int updateCount(Club club) {
        return clubDao.updateCount(club);
    }

    @Override
    public String findChief(Club club) {
        return clubDao.findChief(club);
    }

    @Override
    public int findIdByName(String name) {
        return clubDao.findIdByName(name);
    }

    @Override
    public int count(Integer status) {
        return clubDao.count(status);
    }

    @Override
    public int countMemeber(Club club) {
        int num=clubDao.countClubMember(club);
        club.setNumber(num);
        clubDao.updateCount(club);
        return num;
    }


    @Override
    public  IPage<Club> findListByPage(Integer page, Integer pageCount){
        IPage<Club> wherePage = new Page<>(page, pageCount);
        Club where = new Club();

        return   baseMapper.selectPage(wherePage, Wrappers.query(where));
    }

    @Override
    public int add(Club club){
        return baseMapper.insert(club);
    }

    @Override
    public int delete(Long id){
        return baseMapper.deleteById(id);
    }

    @Override
    public int updateData(Club club){
        return baseMapper.updateById(club);
    }

    @Override
    public Club findById(Long id){
        return  baseMapper.selectById(id);
    }
}
