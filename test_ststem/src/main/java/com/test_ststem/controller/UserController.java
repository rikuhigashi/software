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
import com.test_ststem.entity.Menu;
import com.test_ststem.entity.User;
import com.test_ststem.service.IMenuService;
import com.test_ststem.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;

import java.util.*;

//跨域请求加上@CrossOrigin(origins ={"http://localhost:8080","null"})
// @Controller
@RestController
@CrossOrigin(origins = { "http://localhost:8080", "null" })
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private IMenuService iMenuService;

    @GetMapping("/list")
    public List<User> list() {
        return iUserService.list();
    }

    @GetMapping("/findByNo")
    public Result findByNo(@RequestParam String no) {
        List list = iUserService.lambdaQuery().eq(User::getNo, no).list();
        return list.size() > 0 ? Result.suc(list) : Result.fail();
    }

    // 增加
    @PostMapping("/save")
    public Result save(@RequestBody User user) {

        return iUserService.save(user) ? Result.suc() : Result.fail();
    }

    // 更新
    @PostMapping("/update")
    public Result update(@RequestBody User user) {

        return iUserService.updateById(user) ? Result.suc() : Result.fail();
    }

    // 登录
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        List list = iUserService.lambdaQuery()
                .eq(User::getNo, user.getNo())
                .eq(User::getPassword, user.getPassword())
                .list();

        if (list.size() > 0) {
            User user1 = (User) list.get(0);
            List menuList = iMenuService.lambdaQuery().like(Menu::getMenuright, user1.getRoleId()).list();
            HashMap res = new HashMap();
            res.put("user", user1);
            res.put("menu", menuList);
            return Result.suc(res);
        }

        return Result.fail();
    }

    // 删除
    @GetMapping("/del")
    public Result del(@RequestParam String id) {
        return iUserService.removeById(id) ? Result.suc() : Result.fail();
    }

    // 修改
    @PostMapping("/mod")
    public boolean mod(@RequestBody User user) {
        return iUserService.updateById(user);
    }

    // 新增或修改
    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody User user) {
        return iUserService.saveOrUpdate(user);
    }

    // 删除
    @GetMapping("/delete")
    public boolean delete(Integer id) {
        return iUserService.removeById(id);
    }

    // 查询（模糊、匹配）
    @PostMapping("/listp")
    public Result listp(@RequestBody User user) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>();
        // lambdaQueryWrapper.like(User::getName, user.getName()); //模糊查询
        // lambdaQueryWrapper.eq(User::getName, user.getName()); // 匹配查询

        if (StringUtils.isNotBlank(user.getName())) {
            lambdaQueryWrapper.like(User::getName, user.getName());
        }

        return Result.suc(iUserService.list(lambdaQueryWrapper));
    }

    // 分页
    @PostMapping("/listPage")
    // public List<User> listPage(@RequestBody HashMap map) {
    public List<User> listPage(@RequestBody QueryPageParam query) {
        // System.out.println(query);

        // System.out.println("num===" + query.getPageNum());
        // System.out.println("size===" + query.getPageSize());

        // System.out.println("no===" + param.get("no"));

        // LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>();
        // // lambdaQueryWrapper.like(User::getName, user.getName()); //模糊查询
        // lambdaQueryWrapper.eq(User::getName, user.getName()); //匹配查询
        // return iUserService.list(lambdaQueryWrapper);

        @SuppressWarnings("rawtypes")
        HashMap param = query.getParam();
        String name = (String) param.get("name");
        System.out.println("name===" + param.get("name"));

        Page<User> page = new Page<User>();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>();
        lambdaQueryWrapper.like(User::getName, name);

        @SuppressWarnings("rawtypes")
        IPage result = iUserService.page(page, lambdaQueryWrapper);

        System.out.println("total==" + result.getTotal());

        return result.getRecords();
    }

    // 自定义分页
    @PostMapping("/listPageC")
    public List<User> listPageC(@RequestBody QueryPageParam query) {

        @SuppressWarnings("rawtypes")
        HashMap param = query.getParam();
        String name = (String) param.get("name");
        System.out.println("name===" + param.get("name"));

        Page<User> page = new Page<User>();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        // LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>();
        // lambdaQueryWrapper.like(User::getName, name);

        IPage result = iUserService.pageC(page);

        System.out.println("total==" + result.getTotal());

        return result.getRecords();
    }

    // 自定义sql使用wrapper
    @PostMapping("/listPageCC")
    public List<User> listPageCC(@RequestBody QueryPageParam query) {

        @SuppressWarnings("rawtypes")
        HashMap param = query.getParam();
        String name = (String) param.get("name");

        Page<User> page = new Page<User>();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>();

        lambdaQueryWrapper.like(User::getName, name);

        IPage result = iUserService.pageCC(page, lambdaQueryWrapper);

        System.out.println("total==" + result.getTotal());

        return result.getRecords();
    }

    @PostMapping("/listPageCC1")
    public Result listPageCC1(@RequestBody QueryPageParam query) {

        @SuppressWarnings("rawtypes")
        HashMap param = query.getParam();
        String name = (String) param.get("name");
        String sex = (String) param.get("sex");
        String roleId = (String) param.get("roleId");

        Page<User> page = new Page<User>();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>();

        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
            lambdaQueryWrapper.like(User::getName, name);
        }
        if (StringUtils.isNotBlank(sex)) {
            lambdaQueryWrapper.eq(User::getSex, sex);
        }
        if (StringUtils.isNotBlank(roleId)) {
            lambdaQueryWrapper.eq(User::getRoleId, roleId);
        }

        // lambdaQueryWrapper.like(User::getName, name);

        IPage result = iUserService.pageCC(page, lambdaQueryWrapper);

        System.out.println("total==" + result.getTotal());

        return Result.suc(result.getRecords(), result.getTotal());
    }

}
