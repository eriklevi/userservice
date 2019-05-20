package com.example.userservice.service;


import com.example.userservice.entity.Sniffer;
import com.example.userservice.entity.User;
import com.example.userservice.repository.SnifferRepository;
import com.example.userservice.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final SnifferRepository snifferRepository;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder, SnifferRepository snifferRepository) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.snifferRepository = snifferRepository;
    }

    @Override
    public void addUser(User user, HttpServletResponse response) {
        //Check if the user already exists
        if(usersRepository.existsByUsername(user.getUsername()) || usersRepository.existsByMail(user.getMail())){
            response.setStatus(400);
        }
        else{
            User newUser = new User();
            newUser.setMail(user.getMail());
            newUser.setUsername(user.getUsername());
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));
            newUser.setRole("USER");
            usersRepository.save(newUser);
            response.setStatus(200);
        }
    }

    @Override
    public void addAdmin(User user, HttpServletResponse response) {
        if(usersRepository.existsByUsername(user.getUsername()) || usersRepository.existsByMail(user.getMail())){
            response.setStatus(400);
        }
        else{
            User newUser = new User();
            newUser.setMail(user.getMail());
            newUser.setUsername(user.getUsername());
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));
            List<String> newRoles = new ArrayList<>();
            newRoles.add("USER");
            newRoles.add("SNIFFER");
            newRoles.add("ADMIN");
            newUser.setRoles(newRoles);
            usersRepository.save(newUser);
            response.setStatus(200);
        }
    }

    @Override
    public void addSniffer(Sniffer sniffer, HttpServletResponse response) {
        if(usersRepository.existsByUsername(sniffer.getUsername())){
            response.setStatus(400);
        }
        else{
            Sniffer newSniffer = new Sniffer();
            newSniffer.setUsername(sniffer.getUsername());
            newSniffer.setPassword(passwordEncoder.encode(sniffer.getPassword()));
            List<String> newRoles = new ArrayList<>();
            newRoles.add("SNIFFER");
            newSniffer.setRoles(newRoles);
            snifferRepository.save(newSniffer);
            response.setStatus(200);
        }
    }

    @Override
    public List<User> getUsers(HttpServletResponse response) {
        response.setStatus(200);
        return usersRepository.findAll();
    }

    @Override
    public User getUser(String username, HttpServletResponse response, Principal principal) {
        if(principal.getName().equals(username)){
            Optional<User> user = usersRepository.findByUsername(username);
            if(user.isPresent()){
                response.setStatus(200);
                return user.get();
            } else{
                response.setStatus(400);
                return null;
            }
        } else{
            response.setStatus(403);
            return null;
        }
    }

    @Override
    public User getUser(String username, HttpServletResponse response) {
        Optional<User> user = usersRepository.findByUsername(username);
        if(user.isPresent()){
            response.setStatus(200);
            return user.get();
        } else{
            response.setStatus(400);
            return null;
        }
    }

    @Override
    public void deleteUser(String username, HttpServletResponse response, Principal principal) {
        if(principal.getName().equals(username)){
            response.setStatus(400);
        } else{
            Long result = usersRepository.deleteByUsername(username);
            if(result == 1)
                response.setStatus(200);
            else
                response.setStatus(400);
        }
    }

    @Override
    public void updateUserByUsername(User user, String username, HttpServletResponse response, Principal principal) {
        if(principal.getName().equals(username) && principal.getName().equals(user.getUsername())){
            Optional<User> optionalUser = usersRepository.findByUsername(username);
            if(optionalUser.isPresent()){
                User u = optionalUser.get();
                if(user.getMail() != null)
                    u.setMail(user.getMail());
                if(user.getPassword() != null)
                    u.setPassword(passwordEncoder.encode(user.getPassword()));
                usersRepository.save(u);
                response.setStatus(200);
            } else{
                response.setStatus(400);
            }
        } else{
            response.setStatus(400);
        }
    }

    @Override
    public void restrictedUpdateUserByUsername(User user, String username, HttpServletResponse response) {
        Optional<User> optionalUser = usersRepository.findByUsername(username);
        if(optionalUser.isPresent()){
            User u = optionalUser.get();
            if(usersRepository.existsByUsername(user.getUsername()) || usersRepository.existsByMail(user.getMail()))
                response.setStatus(400);
            else{
                u.setMail(user.getMail());
                u.setUsername(user.getUsername());
                u.setPassword(passwordEncoder.encode(user.getPassword()));
                if(user.getRoles() != null && user.getRoles().size() > 0)
                    u.setRoles(user.getRoles());
                usersRepository.save(u);
                response.setStatus(200);
            }
        }
        else{
            response.setStatus(400);
        }
    }

    @Override
    public List<Sniffer> getSniffersAsUsers() {
        return usersRepository.findAll()
                .stream()
                .filter( user -> {
                    return user.getRoles().size() == 1 && user.getRoles().contains("SNIFFER");
                })
                .map(user -> {
                    Sniffer s = new Sniffer();
                    s.setId(user.getId());
                    s.setUsername(user.getUsername());
                    s.setPassword(user.getPassword());
                    s.setRoles(user.getRoles());
                    return s;
                })
                .collect(Collectors.toList());
    }
}
