package com.example.userservice.controller;

import com.example.userservice.entity.Sniffer;
import com.example.userservice.entity.User;
import com.example.userservice.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/restricted")
@PreAuthorize("hasAuthority('ADMIN')")
public class RestrictedUserController {

    private final UsersService usersService;

    @Autowired
    public RestrictedUserController(UsersService usersService) {
        this.usersService = usersService;
    }

    @RequestMapping(value = "/admins", method = RequestMethod.POST)
    public void createAdmin(@RequestBody @Valid User user, HttpServletResponse response){
        usersService.addAdmin(user, response);
    }

    @RequestMapping(value = "/sniffers", method = RequestMethod.POST)
    public void createSniffer(@RequestBody Sniffer user, HttpServletResponse response){
        usersService.addSniffer(user, response);
    }

    @RequestMapping(value = "/sniffers", method = RequestMethod.GET)
    public List<Sniffer> getSnifferAsUsers(){
        return usersService.getSniffersAsUsers();
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public void createUser(@RequestBody @Valid User user, HttpServletResponse response){
        usersService.addUser(user, response);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public @ResponseBody
    List<User> getUsers(HttpServletResponse response){
        return usersService.getUsers(response);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    public void updateUserByUsername(@RequestBody @Valid User user, @PathVariable String id, HttpServletResponse response){
        usersService.restrictedUpdateUserById(user, id, response);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable @NotNull String id, HttpServletResponse response, Principal principal){
        usersService.deleteUser(id, response, principal);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable @NotNull String id, HttpServletResponse response){
        return usersService.getUser(id, response);
    }
}
