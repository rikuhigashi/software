package com.test_ststem.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test_ststem.entity.Storage;
import com.test_ststem.mapper.StorageMapper;
import com.test_ststem.service.IStorageService;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author higashi
 * @since 2024-07-11
 */
@Service
public class StorageServiceImpl extends ServiceImpl<StorageMapper, Storage> implements IStorageService {

    @Resource
    private StorageMapper storageMapper;

    @Override
    public IPage pageCC(Page<Storage> page, Wrapper wrapper) {
        return storageMapper.pageCC(page, wrapper);
    }

}
