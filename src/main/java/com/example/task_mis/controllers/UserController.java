package com.example.task_mis.controllers;


import com.example.task_mis.dto.UserData;
import com.example.task_mis.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "api/users")
public class UserController {
    //Injected UserService into Controller to access the methods
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //list all of Users
    @GetMapping
    public List<UserData> getListOfUsers(){return userService.getListOfUsers();}


}
