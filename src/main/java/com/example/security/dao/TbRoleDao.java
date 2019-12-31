package com.example.security.dao;

import com.example.security.entity.TbRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TbRoleDao extends BaseDao<TbRole>{
    List<TbRole> findRoleByUserId(@Param("userId") long userId);
}
