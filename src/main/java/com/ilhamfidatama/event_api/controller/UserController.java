package com.ilhamfidatama.event_api.controller;

import com.ilhamfidatama.event_api.model.Login;
import com.ilhamfidatama.event_api.model.Register;
import com.ilhamfidatama.event_api.model.Response;
import com.ilhamfidatama.event_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "users", name = "user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/signIn")
    public Response<Object> userLogin(@RequestBody Login login) {
        return userService.loginUser(login.getUsername(), login.getPassword());
    }

    @GetMapping("/{userId}")
    public Response<Object> findUserById(@PathVariable String userId) {
        return userService.findUserById(userId);
    }

    @DeleteMapping("/{userId}")
    public Response<Object> deleteUserById(@PathVariable String userId) {
        return userService.deleteUser(userId);
    }

    @PostMapping
    public Response<Object> createUser(@RequestBody Register account) {
        return userService.createUser(account.getUsername(), account.getEmail(), account.getFullname(),
                account.getAddress(), account.getPassword());
    }

}
