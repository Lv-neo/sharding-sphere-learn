package com.neo.shardingsphere.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "user")
@Table(name = "t_user")
public class UserEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -6171110531081112401L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "name",length = 32)
    private String name;
    @Column(name = "age",length = 16)
    private int age;

}