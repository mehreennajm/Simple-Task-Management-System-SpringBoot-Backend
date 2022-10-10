package com.example.task_mis.controllers;
import com.example.task_mis.dto.UserData;
import com.example.task_mis.entities.User;
import com.example.task_mis.enums.UserRole;
import com.example.task_mis.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;


@SpringBootApplication
@CrossOrigin (origins = "http://localhost:4200/")
@RestController
@RequestMapping({"api"})
public class UserController {

    @Autowired
    private ModelMapper modelMapper;
    //Injected UserService into Controller to access the methods

    @Autowired
    private  UserService userService;

    //list all of Users
    @GetMapping({"/users"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserData> getListOfUsers(){return userService.getListOfUsers();}

    @GetMapping({"/users/managers"})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    public List<UserData> getListOfOrdinaryUsers(){return userService.getListOfOrdinaryUsers();}


    @PostMapping({"/users/add-user"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<User> addNewUser(@RequestParam("profilePhoto") MultipartFile profilePhoto,
                                           @RequestParam("firstName") String firstName,
                                           @RequestParam("lastName") String lastName,
                                           @RequestParam("email") String email,
                                           @RequestParam("password") String password,
                                           @RequestParam("userRole") UserRole userRole) throws IOException {

        userService.addNewUser(profilePhoto,firstName,lastName,email,password,userRole);

        return ResponseEntity.ok().build();
    }

    // update User record
    @Transactional
    @PutMapping({"/users/{id}/edit"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<User> updateUserRecord(@PathVariable Long id,
                                                     @RequestParam("profilePhoto") MultipartFile profilePhoto,
                                                     @RequestParam("firstName") String firstName,
                                                     @RequestParam("lastName") String lastName,
                                                     @RequestParam("email") String email,
                                                     @RequestParam("password") String password,
                                                     @RequestParam("userRole") UserRole userRole) throws IOException {



        userService.updateUser(id,profilePhoto,firstName,lastName,email,password,userRole);
        return ResponseEntity.ok().build();
    }


    //getting a specific User via id
    @GetMapping({"/users/{id}"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User getSpecificRecord(@PathVariable(value = "id") Long userId){
        User user = userService.getSpecificUserRecord(userId);
        return user;
    }

    @DeleteMapping({"/users/{id}/delete"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deletePost(@PathVariable(name = "id") Long userId) {
        userService.deleteUser(userId);
    }





}
