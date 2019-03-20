package com.neo.shardingsphere.controller;

import com.google.gson.Gson;
import com.neo.shardingsphere.entity.OrderEntity;
import com.neo.shardingsphere.entity.UserEntity;
import com.neo.shardingsphere.service.OrderService;
import com.neo.shardingsphere.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private OrderService orderService;

    @RequestMapping("/save")
    public String saveUser() {
        UserEntity user = new UserEntity(1, "张三", 22);
        userService.saveUser(user);
        user = new UserEntity(2, "张三", 22);
        userService.saveUser(user);

        user = new UserEntity(3, "张三", 22);
        userService.saveUser(user);

        user = new UserEntity(4, "张三", 22);
        userService.saveUser(user);

        OrderEntity order = new OrderEntity(1,1,1);
        orderService.saveOrder(order);

        order = new OrderEntity(2,2,1);
        orderService.saveOrder(order);

        order = new OrderEntity(3,3,1);
        orderService.saveOrder(order);

        return "success";
    }

    @RequestMapping("/getUser")
    public String getUsers() {
        List<UserEntity> users = userService.getUsers();
        return new Gson().toJson(users);
    }

}
