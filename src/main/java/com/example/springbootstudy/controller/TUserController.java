package com.example.springbootstudy.controller;

import com.example.springbootstudy.entity.User;
import com.example.springbootstudy.utils.JwtUtils;
import com.example.springbootstudy.utils.Result;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tuser")
@CrossOrigin
public class TUserController {
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        String token = JwtUtils.generateToken(user.getUsername());
        return Result.ok().data("token",token);
    }
}
