package com.test_ststem.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.test_ststem.entity.Goods;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author higashi
 * @since 2024-07-13
 */
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

    IPage pageCC(Page<Goods> page, @Param(Constants.WRAPPER) LambdaQueryWrapper<Goods> lambdaQueryWrapper);

}
