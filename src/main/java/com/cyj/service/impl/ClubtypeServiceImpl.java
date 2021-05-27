package com.cyj.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyj.dao.ClubMapper;
import com.cyj.dao.ClubtypeMapper;
import com.cyj.domain.Club;
import com.cyj.domain.Clubtype;
import com.cyj.service.IClubtypeService;
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
public class ClubtypeServiceImpl extends ServiceImpl<ClubtypeMapper, Clubtype> implements IClubtypeService {

    @Autowired
    private ClubtypeMapper clubtypeDao;

    @Override
    public PageInfo<Clubtype> queryClubtypeAll(int page, int pageSize,Clubtype clubtype) {
        PageHelper.startPage(page, pageSize);
//        查询的结果集
        List<Clubtype> list = clubtypeDao.queryClubtypeAll(clubtype);
        PageInfo<Clubtype> pageInfo = new PageInfo<>(list);

        return pageInfo;
    }

    @Override
    public List<Clubtype> findAll() {
        return baseMapper.selectList(null);
    }

    @Override
    public List<Clubtype> findAllClubtype(Clubtype clubtype) {
        return clubtypeDao.findAllClubtype(clubtype);
    }

    @Override
    public  IPage<Clubtype> findListByPage(Integer page, Integer pageCount){
        IPage<Clubtype> wherePage = new Page<>(page, pageCount);
        Clubtype where = new Clubtype();

        return   baseMapper.selectPage(wherePage, Wrappers.query(where));
    }

    @Override
    public int add(Clubtype clubtype){
        return baseMapper.insert(clubtype);
    }

    @Override
    public int delete(Long id){
        return baseMapper.deleteById(id);
    }

    @Override
    public int updateData(Clubtype clubtype){
        return baseMapper.updateById(clubtype);
    }

    @Override
    public Clubtype findById(Long id){
        return  baseMapper.selectById(id);
    }
}
