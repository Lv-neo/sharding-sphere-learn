package com.neo.shardingsphere.repository;

import com.neo.shardingsphere.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

}