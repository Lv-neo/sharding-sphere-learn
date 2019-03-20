package com.neo.shardingsphere.service;

import com.neo.shardingsphere.entity.OrderEntity;
import com.neo.shardingsphere.entity.UserEntity;
import com.neo.shardingsphere.repository.OrderRepository;
import com.neo.shardingsphere.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public void saveOrder(OrderEntity orderEntity) {
        orderRepository.save(orderEntity);
    }

    public List<OrderEntity> getOrders() {
        return orderRepository.findAll();
    }
}
