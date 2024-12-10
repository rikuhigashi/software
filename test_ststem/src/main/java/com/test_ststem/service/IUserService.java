package com.test_ststem.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.test_ststem.entity.User;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author higashi
 * @since 2024-06-10
 */
public interface IUserService extends IService<User> {

    IPage pageC(Page<User> page);

    IPage pageCC(IPage<User> page, Wrapper wrapper);

}
