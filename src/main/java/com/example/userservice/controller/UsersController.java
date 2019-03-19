package com.example.userservice.controller;


import com.example.userservice.entity.User;
import com.example.userservice.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.List;

@RestController
@Validated
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public void createUser(@RequestBody @Valid User user, HttpServletResponse response){
        usersService.addUser(user, response);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public @ResponseBody
    List<User> getUsers(HttpServletResponse response, Principal principal){
        return usersService.getUsers(response);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable @NotNull String id, HttpServletResponse response){
        usersService.deleteUser(id, response);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody
    User getUser(@PathVariable String id, HttpServletResponse response){
        return usersService.getUserById(id, response);
    }

    /*@PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void updateUserById(@RequestBody @Valid User user, @PathVariable String id, HttpServletResponse response){
        usersService.updateUserById(user, id, response);
    }*/
}

