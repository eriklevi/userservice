package com.example.userservice.controller;


import com.example.userservice.entity.User;
import com.example.userservice.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RestController
@Validated
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public @ResponseBody
    User getUser(@PathVariable String username, HttpServletResponse response, Principal principal){
        return usersService.getUser(username, response, principal);
    }

    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping(value = "/{username}", method = RequestMethod.PUT)
    public void updateUser(@PathVariable String username,@RequestBody User user, HttpServletResponse response, Principal principal){
        usersService.updateUserByUsername(user,username, response, principal);
    }

}

