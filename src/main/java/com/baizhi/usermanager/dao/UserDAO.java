package com.baizhi.usermanager.dao;


import com.baizhi.usermanager.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserDAO {

    //根据用户名和密码查询用户对象
    public User selectUserByNameAndPwd(User user);

    //根据用户名查询用户对象
    @Select("select * from t_user where username = #{username}")
    public User selectUserByName(String username);

    //添加
    @Insert(" insert into t_user values(null,#{username},#{password})")
    public void insert(User user);

    //修改
    public void update(User user);


    public List<User> select();
}
