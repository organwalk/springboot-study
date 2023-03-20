package com.example.springbootstudy.controller;
import com.example.springbootstudy.entity.User;
import org.springframework.web.bind.annotation.*;

@RestController
public class ParamsController {

    //发送GET请求
    @RequestMapping(value = "/getTest1",method = RequestMethod.GET)
    public String getTest1(){
        return "GET请求";
    }
    //发送含参的GET请求
    @RequestMapping(value = "/getTest2",method = RequestMethod.GET)
    public String getTest1(String nickname,String phone){
        System.out.println("nickname"+nickname);
        System.out.println("phone"+phone);
        return "GET请求"+nickname+phone;
    }

    //适用于前后端参数名称不统一
    @RequestMapping(value = "/getTest3",method = RequestMethod.GET)
    public String getTest1(@RequestParam(value = "nickname",required = false) String name){
        //required意味着该参数传不传递都可以
        System.out.println("nickname"+name);
        return "GET请求"+name;
    }

    //使用apipost发送请求
    @RequestMapping(value = "/postTest1",method = RequestMethod.POST)
    public String postTest1(){
        return "POST请求";
    }
    //使用apipost发送含参请求
    @RequestMapping(value = "/postTest2",method = RequestMethod.POST)
    public String postTest2(String username,String password){
        System.out.println("username:"+username);
        System.out.println("password:"+password);
        return "POST";
    }
    //以实体类存储请求参数
    @RequestMapping(value = "/postTest3",method = RequestMethod.POST)
    public String postTest3(User user){
        System.out.println(user);
        return "POST";
    }
    //使用apipost发送json格式数据
    @RequestMapping(value = "/postTest4",method = RequestMethod.POST)
    public String postTest4(@RequestBody User user){
        System.out.println(user);
        return "POST";
    }
    //通配符请求
    @GetMapping("/test/**")
    public String test(){
        return "通配符请求";
    }
}
