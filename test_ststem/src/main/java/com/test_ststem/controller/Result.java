package com.test_ststem.controller;

import lombok.Data;

@Data
public class Result {
    private int code; // 编码 200/400
    private String msg; // 成功或失败
    private Long total; // 总记录数
    private Object data; // 数据

    public static Result fail() { // 失败方法
        return result(400, "失败", 0L, null);
    }

    public static Result suc() { // 成功
        return result(200, "成功", 0L, null);
    }

    public static Result suc(Object data) { // 成功
        return result(200, "成功", 0L, data);
    }

    public static Result suc(Object data, Long total) { // 成功
        return result(200, "成功", total, data);
    }

    private static Result result(int code, String msg, Long total, Object data) { // 构造方法
        Result res = new Result();
        res.setData(data);
        res.setMsg(msg);
        res.setCode(code);
        res.setTotal(total);
        return res;
    }
}
