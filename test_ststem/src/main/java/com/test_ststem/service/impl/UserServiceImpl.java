package com.test_ststem.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.test_ststem.entity.User;
import com.test_ststem.mapper.UserMapper;
import com.test_ststem.service.IUserService;

import jakarta.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author higashi
 * @since 2024-06-10
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
   
    @Resource
    private UserMapper userMapper;

    @Override
    public IPage pageC(Page<User> page) {
        return userMapper.PageC(page);
    }

    @Override
    public IPage pageCC(IPage<User> page, Wrapper wrapper) {
        return userMapper.pageCC(page,wrapper);
    }

}
