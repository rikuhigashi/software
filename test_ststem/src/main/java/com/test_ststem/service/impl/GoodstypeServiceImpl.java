package com.test_ststem.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test_ststem.entity.Goodstype;
import com.test_ststem.entity.Storage;
import com.test_ststem.mapper.GoodstypeMapper;
import com.test_ststem.service.IGoodstypeService;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author higashi
 * @since 2024-07-13
 */
@Service
public class GoodstypeServiceImpl extends ServiceImpl<GoodstypeMapper, Goodstype> implements IGoodstypeService {

    @Resource
    private GoodstypeMapper goodstypeMapper;

    @Override
    public IPage pageCC(Page<Goodstype> page, LambdaQueryWrapper<Goodstype> lambdaQueryWrapper) {
       
        return goodstypeMapper.pageCC(page,lambdaQueryWrapper);
    }


}
