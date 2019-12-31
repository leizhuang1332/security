package com.example.security.dao;

import com.example.security.entity.TbRolePermission;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TbRolePermissionDao extends BaseDao<TbRolePermission> {
    List<TbRolePermission> getRolePermissions();
}
