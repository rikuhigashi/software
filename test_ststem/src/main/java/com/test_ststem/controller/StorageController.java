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
import com.test_ststem.entity.Storage;

import com.test_ststem.service.IStorageService;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author higashi
 * @since 2024-07-11
 */
@RestController
@CrossOrigin(origins = { "http://localhost:8080", "null" })
@RequestMapping("/storage")
public class StorageController {

    @Autowired
    private IStorageService iStorageService;

    // 增加
    @PostMapping("/save")
    public Result save(@RequestBody Storage storage) {

        return iStorageService.save(storage) ? Result.suc() : Result.fail();
    }

    // 更新
    @PostMapping("/update")
    public Result update(@RequestBody Storage storage) {

        return iStorageService.updateById(storage) ? Result.suc() : Result.fail();
    }

    // 删除
    @GetMapping("/del")
    public Result del(@RequestParam String id) {
        return iStorageService.removeById(id) ? Result.suc() : Result.fail();
    }

    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query) {

        HashMap param = query.getParam();
        String name = (String) param.get("name");

        Page<Storage> page = new Page<Storage>();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Storage> lambdaQueryWrapper = new LambdaQueryWrapper<Storage>();

        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
            lambdaQueryWrapper.like(Storage::getName, name);
        }

        IPage result = iStorageService.pageCC(page, lambdaQueryWrapper);

        return Result.suc(result.getRecords(), result.getTotal());
    }

    @GetMapping("/list")
    public Result list() {
        List list = iStorageService.lambdaQuery().list();
        return Result.suc(list);
    }
}
