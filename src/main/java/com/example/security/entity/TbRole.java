package com.example.security.entity;

import lombok.Data;

@Data
public class TbRole {

    private long id;
    private long parentId;
    private String name;
    private String enname;
    private String description;
}
