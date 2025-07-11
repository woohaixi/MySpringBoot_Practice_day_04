package com.hwadee.springboot.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long userId;

    private String name;

    private Integer age;

    private String email;

    private int isDelete;
}
