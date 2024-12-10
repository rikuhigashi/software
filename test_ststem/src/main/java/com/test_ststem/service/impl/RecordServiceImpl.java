package com.test_ststem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test_ststem.mapper.RecordMapper;
import com.test_ststem.service.IRecordService;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Service;
import com.test_ststem.entity.Record;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author higashi
 * @since 2024-07-15
 */
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements IRecordService {
    @Resource
    private RecordMapper recordMapper;



    @Override
    public IPage pageCC(Page<Record> page, QueryWrapper<Record> queryWrapper) {
        return recordMapper.pageCC(page,queryWrapper);
    }

}
