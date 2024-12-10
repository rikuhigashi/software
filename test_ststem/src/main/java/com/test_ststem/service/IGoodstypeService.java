package com.test_ststem.service;



import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.test_ststem.entity.Goodstype;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author higashi
 * @since 2024-07-13
 */
public interface IGoodstypeService extends IService<Goodstype> {

    IPage pageCC(Page<Goodstype> page, LambdaQueryWrapper<Goodstype> lambdaQueryWrapper);

    
}
