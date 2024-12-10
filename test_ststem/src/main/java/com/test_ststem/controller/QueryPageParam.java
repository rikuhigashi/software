package com.test_ststem.controller;

import java.util.HashMap;

import lombok.Data;

//分页
@Data
public class QueryPageParam {
    // 最大页数为20
    private static int PAGE_SIZE = 20;
    // 默认第一页
    private static int PAGE_NUM = 1;

    private int pageSize = PAGE_SIZE;
    private int pageNum = PAGE_NUM;

    @SuppressWarnings("rawtypes")
    private HashMap param = new HashMap<>();
}
