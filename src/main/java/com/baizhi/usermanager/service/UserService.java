package com.baizhi.usermanager.service;

import com.baizhi.usermanager.entity.User;

public interface UserService {

    //根据用户名和密码查询用户对象
    public User selectUserByNameAndPwd(User user);

    //根据用户名查询用户对象
    public User selectUserByName(String username);

    //添加
    public void insert(User user);

    //修改
    public void update(User user);

}
