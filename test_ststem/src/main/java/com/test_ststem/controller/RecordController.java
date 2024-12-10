package com.test_ststem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.test_ststem.entity.Goods;
import com.test_ststem.entity.Record;
import com.test_ststem.service.IGoodsService;
import com.test_ststem.service.IRecordService;

import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author higashi
 * @since 2024-07-15
 */
@RestController
@CrossOrigin(origins = { "http://localhost:8080", "null" })
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private IRecordService iRecordService;

    @Autowired
    private IGoodsService iGoodsService;

    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query) {

        HashMap param = query.getParam();
        String name = (String) param.get("name");
        String goodstype = (String) param.get("goodstype");
        String storage = (String) param.get("storage");
        String roleId = (String) param.get("roleId");
        String userId = (String) param.get("userId");

        Page<Record> page = new Page<Record>();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        QueryWrapper<Record> queryWrapper = new QueryWrapper<Record>();

        if ("2".equals(roleId)) {
            queryWrapper.apply("a.userid=" + userId);
        }

        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
            // lambdaQueryWrapper.like(Record::getName, name);
            queryWrapper.like("g.name", name);
        }
        if (StringUtils.isNotBlank(goodstype) && !"null".equals(goodstype)) {
            // lambdaQueryWrapper.eq(Record::getGoodstype, goodstype);
            queryWrapper.eq("g2.id", goodstype);
        }
        if (StringUtils.isNotBlank(storage) && !"null".equals(storage)) {
            // lambdaQueryWrapper.eq(Record::getStorage, storage);
            queryWrapper.eq("s.id", storage);
        }

        IPage result = iRecordService.pageCC(page, queryWrapper);

        return Result.suc(result.getRecords(), result.getTotal());
    }

    // 增加
    @PostMapping("/save")
    public Result save(@RequestBody Record record) {

        Goods goods = iGoodsService.getById(record.getGoods());
        int n = record.getCount();

        if ("2".equals(record.getAction())) {
            n = -n;
            record.setCount(n);
        }

        int num = n + goods.getCount();
        goods.setCount(num);
        iGoodsService.updateById(goods);

        return iRecordService.save(record) ? Result.suc() : Result.fail();
    }
}
