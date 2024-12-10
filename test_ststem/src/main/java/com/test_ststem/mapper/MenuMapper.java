package com.test_ststem.mapper;


import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.test_ststem.entity.Menu;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author higashi
 * @since 2024-07-05
 */

 @Mapper
public interface MenuMapper extends BaseMapper<Menu> {

}
