package com.example.userservice.service;


import com.example.userservice.entity.User;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UsersService {
    void addUser(User user, HttpServletResponse response);
    List<User> getUsers(HttpServletResponse response);
    User getUserById(String id, HttpServletResponse response);
    void deleteUser(String id, HttpServletResponse response);
    void updateUserById(User user, String id, HttpServletResponse response);
}
