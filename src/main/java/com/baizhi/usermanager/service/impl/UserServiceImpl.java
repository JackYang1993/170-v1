package com.baizhi.usermanager.service.impl;


import com.baizhi.usermanager.dao.UserDAO;
import com.baizhi.usermanager.entity.User;
import com.baizhi.usermanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public User selectUserByNameAndPwd(User user) {
        return userDAO.selectUserByNameAndPwd(user);
    }

    @Override
    public User selectUserByName(String username) {
        User user = null;
        try {
            user = userDAO.selectUserByName(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void insert(User user) {
        userDAO.insert(user);
    }

    @Override
    public void update(User user) {
        userDAO.update(user);
    }
}
