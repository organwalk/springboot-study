package com.example.springbootstudy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootstudy.entity.User;
import com.example.springbootstudy.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DUserController {
    @Autowired//自动注入mapper
    private UserMapper userMapper;
    @GetMapping("/duser")
    public List findAll(){
        List<User> list =  userMapper.selectAllUserAndOrders();
        System.out.println(list);
        return list;
    }
    //条件查询
    @GetMapping("/duser/find")
    public List<User> findByCond(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username","lucy");
        return userMapper.selectList(queryWrapper);
    }
    //分页查询
    @GetMapping("/duser/findbypage")
    public IPage findByPage(){
        //设置起始值及每页条数
        Page<User> page = new Page<>(0,2);
        IPage iPage = userMapper.selectPage(page,null);
        return iPage;
    }
}
