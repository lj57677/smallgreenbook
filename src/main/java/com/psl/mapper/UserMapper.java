package com.psl.mapper;

import com.psl.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;

public interface UserMapper {

    //通过用户名查询用户
    @Select("select * from tb_user where username = #{username}")
    User queryByUsername(String username);

    //增加新用户
    @Insert("insert into tb_user(username, password, create_time, update_time) values(#{username}, #{password}, #{now}, #{now})")
    void userRegister(String username, String password, LocalDateTime now);

    //更新用户信息
    Integer update(User user);

    @Update("update tb_user set user_pic = #{avatarUrl}, update_time = now() where id = #{id}")
    Integer updateAvatar(String avatarUrl, Integer id);

    @Update("update tb_user set password = #{md5NewPwd}, update_time = now() where username = #{username}")
    Integer updatePwd(String md5NewPwd, String username);
}
