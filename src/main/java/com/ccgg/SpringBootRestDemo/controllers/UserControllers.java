package com.ccgg.SpringBootRestDemo.controllers;

import com.ccgg.SpringBootRestDemo.beans.CcggUser;
import com.ccgg.SpringBootRestDemo.http.Response;
import com.ccgg.SpringBootRestDemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/users")
public class UserControllers {
    @Autowired
    UserService userService;
    @PostMapping
    public Response addUser(@RequestBody CcggUser user) {
        return userService.register(user);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public Response deleteUser(@PathVariable int id) {
        return userService.deleteUser(id);
    }

}