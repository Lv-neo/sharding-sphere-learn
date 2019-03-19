package com.neo.shardingsphere.controller;

import com.google.gson.Gson;
import com.neo.shardingsphere.entity.UserEntity;
import com.neo.shardingsphere.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/save")
    public String saveUser() {
        UserEntity user = new UserEntity(1, "张三", 22);
        userService.saveUser(user);
        return "success";
    }

    @PostMapping("/getUser")
    public String getUsers() {
        List<UserEntity> users = userService.getUsers();
        return new Gson().toJson(users);
    }

}
