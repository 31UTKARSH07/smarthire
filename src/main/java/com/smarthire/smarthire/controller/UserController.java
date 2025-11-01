package com.smarthire.smarthire.controller;

import com.smarthire.smarthire.model.User;
import com.smarthire.smarthire.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        return ResponseEntity.ok(userService.createUser(user));
    }
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getUsersById(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updateUser){
        return ResponseEntity.ok(userService.updateUser(id,updateUser));
    }
    public ResponseEntity<String>deleteUser(@PathVariable Long id){
        userService.deleteUserById(id);
        return ResponseEntity.ok("User has been deleted");
    }





}
