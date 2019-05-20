package com.example.userservice.service;


import com.example.userservice.entity.Sniffer;
import com.example.userservice.entity.User;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

public interface UsersService {
    void addUser(User user, HttpServletResponse response);
    void addAdmin(User user, HttpServletResponse response);
    void addSniffer(Sniffer sniffer, HttpServletResponse response);
    List<User> getUsers(HttpServletResponse response);
    User getUser(String username, HttpServletResponse response, Principal principal);
    User getUser(String username, HttpServletResponse response);
    void deleteUser(String username, HttpServletResponse response, Principal principal);
    void updateUserByUsername(User user, String username, HttpServletResponse response, Principal principal);
    void restrictedUpdateUserByUsername(User user, String username, HttpServletResponse response);
    List<Sniffer> getSniffersAsUsers();
}
