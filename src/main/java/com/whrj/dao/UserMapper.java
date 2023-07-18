package com.whrj.dao;

import com.whrj.base.BaseMapper;
import com.whrj.vo.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User,Integer> {
    public User queryByUserName(String userName);
}