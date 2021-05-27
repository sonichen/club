package com.cyj.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyj.dao.FinanceMapper;
import com.cyj.domain.Club;
import com.cyj.domain.Finance;
import com.cyj.service.IFinanceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 财务项目表 服务实现类
 * </p>
 *
 * @author cyj
 * @since 2021-05-15
 */
@Service
public class FinanceServiceImpl extends ServiceImpl<FinanceMapper, Finance> implements IFinanceService {

    @Autowired
    private FinanceMapper financeMapper;

    @Override
    public int deleteByClubId(Integer clubId) {
        return financeMapper.deleteByClubId(clubId);
    }

    @Override
    public PageInfo<Finance> queryFinanceAll(int page, int pageSize,Finance finance) {
        PageHelper.startPage(page, pageSize);
//        查询的结果集
        List<Finance> list = financeMapper.queryFinanceAll(finance);
        PageInfo<Finance> pageInfo = new PageInfo<>(list);

        return pageInfo;
    }

    @Override
    public  IPage<Finance> findListByPage(Integer page, Integer pageCount){
        IPage<Finance> wherePage = new Page<>(page, pageCount);
        Finance where = new Finance();

        return   baseMapper.selectPage(wherePage, Wrappers.query(where));
    }

    @Override
    public int add(Finance finance){
        return baseMapper.insert(finance);
    }

    @Override
    public int delete(Long id){
        return baseMapper.deleteById(id);
    }

    @Override
    public int updateData(Finance finance){
        return baseMapper.updateById(finance);
    }

    @Override
    public Finance findById(Long id){
        return  baseMapper.selectById(id);
    }
}
