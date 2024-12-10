package com.test_ststem.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.test_ststem.entity.Record;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author higashi
 * @since 2024-07-15
 */
public interface IRecordService extends IService<Record> {

    IPage pageCC(Page<Record> page, QueryWrapper<Record> queryWrapper);

}
