package com.example.security.entity;

import lombok.Data;

@Data
public class TbRolePermission {
    private String permissionName;
    private String permissionEnname;
    private String permissionUrl;

    private String roleEnname;
    private String roleName;
}
