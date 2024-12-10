package com.test_ststem.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test_ststem.entity.Goods;
import com.test_ststem.mapper.GoodsMapper;
import com.test_ststem.service.IGoodsService;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author higashi
 * @since 2024-07-13
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

    @Resource
    private GoodsMapper goodsMapper;

    @Override
    public IPage pageCC(Page<Goods> page, LambdaQueryWrapper<Goods> lambdaQueryWrapper) {
        return goodsMapper.pageCC(page,lambdaQueryWrapper);
    }

}
