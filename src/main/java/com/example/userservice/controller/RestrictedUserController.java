package com.example.userservice.controller;

import com.example.userservice.entity.User;
import com.example.userservice.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/restricted")
@PreAuthorize("hasAuthority('ADMIN')")
public class RestrictedUserController {

    private final UsersService usersService;

    @Autowired
    public RestrictedUserController(UsersService usersService) {
        this.usersService = usersService;
    }

    @RequestMapping(value = "/admin", method = RequestMethod.POST)
    public void createAdmin(@RequestBody @Valid User user, HttpServletResponse response){
        usersService.addAdmin(user, response);
    }

    @RequestMapping(value = "/sniffer", method = RequestMethod.POST)
    public void createSniffer(@RequestBody @Valid User user, HttpServletResponse response){
        usersService.addSniffer(user, response);
    }
}
