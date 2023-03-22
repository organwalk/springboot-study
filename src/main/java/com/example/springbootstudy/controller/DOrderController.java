package com.example.springbootstudy.controller;

import com.example.springbootstudy.entity.Order;
import com.example.springbootstudy.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DOrderController {
    @Autowired//自动注入mapper
    private OrderMapper orderMapper;
    @GetMapping("/dorder")
    public List findAll(){
        List<Order> list = orderMapper.selectAllOrdersAndUser();
        System.out.println(list);
        return list;
    }
}
