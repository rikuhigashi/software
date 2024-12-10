package com.test_ststem.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.test_ststem.entity.Storage;


/**
 * <p>
 * 服务类
 * </p>
 *
 * @author higashi
 * @since 2024-07-11
 */
public interface IStorageService extends IService<Storage> {

    IPage pageCC(Page<Storage> page, Wrapper wrapper);

}
