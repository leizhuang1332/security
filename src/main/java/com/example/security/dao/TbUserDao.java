package com.example.security.dao;

import com.example.security.entity.TbUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface TbUserDao extends BaseDao<TbUser>{
    TbUser findByUsername(@Param("username") String username);
}
