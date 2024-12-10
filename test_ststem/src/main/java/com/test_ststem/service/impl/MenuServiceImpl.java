package com.test_ststem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test_ststem.entity.Menu;
import com.test_ststem.mapper.MenuMapper;
import com.test_ststem.service.IMenuService;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author higashi
 * @since 2024-07-05
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

}
