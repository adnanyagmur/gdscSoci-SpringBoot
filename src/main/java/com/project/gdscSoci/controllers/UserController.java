package com.project.gdscSoci.controllers;


import com.project.gdscSoci.entities.User;
import com.project.gdscSoci.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/allUsers")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/createUser")
    public User createUser(@RequestBody User newUser){
        return userService.saveOneUser(newUser);
    }

    @GetMapping("/oneUser/{userId}")
    public User getOneUser(@PathVariable Long userId){
        return userService.getOneUserById(userId);
    }

    @PutMapping("/updateOneUser/{userId}")
    public User updateOneUser(@PathVariable Long userId, @RequestBody User newUser){
        return userService.updateOneUser(userId,newUser);
    }

    @DeleteMapping("/deleteOneUser/{userId}")
    public void deleteOneUser(@PathVariable Long userId){
        userService.deleteById(userId);
    }
}
