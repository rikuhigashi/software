package com.test_ststem.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.test_ststem.entity.Record;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author higashi
 * @since 2024-07-15
 */
@Mapper
public interface RecordMapper extends BaseMapper<Record> {

    IPage pageCC(Page<Record> page,@Param(Constants.WRAPPER) QueryWrapper<Record> queryWrapper);

}
