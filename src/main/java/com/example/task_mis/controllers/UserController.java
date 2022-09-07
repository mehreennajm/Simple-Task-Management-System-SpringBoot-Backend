package com.example.task_mis.controllers;
import com.example.task_mis.dto.UserData;
import com.example.task_mis.entities.User;
import com.example.task_mis.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import java.util.List;


@SpringBootApplication
@CrossOrigin (origins = "http://localhost:4200/")
@RestController
@RequestMapping(path = "api/users")
public class UserController {

    @Autowired
    private ModelMapper modelMapper;
    //Injected UserService into Controller to access the methods

    @Autowired
    private  UserService userService;
    //list all of Users
    @GetMapping
    public List<UserData> getListOfUsers(){return userService.getListOfUsers();}



    @PostMapping(path = "/add-user")
    public void addNewUser(@RequestBody User user){
        userService.addNewUser(user);
    }

    // update User record
    @Transactional
    @PutMapping("/{id}/edit")
    public ResponseEntity<UserData> updateUserRecord(@PathVariable Long id, @RequestBody UserData userData) {

        // convert DTO to Entity
        User userRequest = modelMapper.map(userData, User.class);
        User newUser = userService.updateUser(id, userRequest);
        // entity to DTO
        UserData userResponse = modelMapper.map(newUser, UserData.class);
        return ResponseEntity.ok().body(userResponse);
    }


    //getting a specific User via id
    @GetMapping("/{id}")
    public User getSpecificRecord(@PathVariable(value = "id") Long userId){
        User user = userService.getSpecificUserRecord(userId);
        return user;
    }

    @DeleteMapping("/{id}/delete")
    public void deletePost(@PathVariable(name = "id") Long userId) {
        userService.deleteUser(userId);
    }





}
