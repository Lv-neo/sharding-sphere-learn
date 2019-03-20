package com.neo.shardingsphere.repository;

import com.neo.shardingsphere.entity.OrderEntity;
import com.neo.shardingsphere.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

}