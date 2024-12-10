package com.test_ststem.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.test_ststem.entity.Goodstype;

import com.test_ststem.service.IGoodstypeService;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author higashi
 * @since 2024-07-13
 */
@RestController
@CrossOrigin(origins = { "http://localhost:8080", "null" })
@RequestMapping("/goodstype")
public class GoodstypeController {
    @Autowired
    private IGoodstypeService iGoodstypeService;

    // 增加
    @PostMapping("/save")
    public Result save(@RequestBody Goodstype goodstype) {

        return iGoodstypeService.save(goodstype) ? Result.suc() : Result.fail();
    }

    // 更新
    @PostMapping("/update")
    public Result update(@RequestBody Goodstype goodstype) {

        return iGoodstypeService.updateById(goodstype) ? Result.suc() : Result.fail();
    }

    // 删除
    @GetMapping("/del")
    public Result del(@RequestParam String id) {
        return iGoodstypeService.removeById(id) ? Result.suc() : Result.fail();
    }

    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query) {

        HashMap param = query.getParam();
        String name = (String) param.get("name");

        Page<Goodstype> page = new Page<Goodstype>();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Goodstype> lambdaQueryWrapper = new LambdaQueryWrapper<Goodstype>();

        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
            lambdaQueryWrapper.like(Goodstype::getName, name);
        }

        IPage result = iGoodstypeService.pageCC(page, lambdaQueryWrapper);

        return Result.suc(result.getRecords(), result.getTotal());
    }

    
    @GetMapping("/list")
    public Result list() {
        List list = iGoodstypeService.lambdaQuery().list();
        return Result.suc(list);
    }
}
