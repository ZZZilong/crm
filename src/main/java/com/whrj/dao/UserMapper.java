package com.whrj.dao;

import com.whrj.base.BaseMapper;
import com.whrj.vo.User;
import org.apache.ibatis.annotations.MapKey;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserMapper extends BaseMapper<User,Integer> {
    public User queryByUserName(String userName);
    /**
     * 查询所有销售人员
     * @return
     */


    @MapKey("id")
    public List<Map<String,Object>> selectSaleman();

}