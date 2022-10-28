package com.example.task_mis.controllers;
import com.example.task_mis.dto.UserData;
import com.example.task_mis.entities.User;
import com.example.task_mis.enums.UserRole;
import com.example.task_mis.services.interfaces.UserService;
import org.modelmapper.ConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.*;
import java.util.List;


@SpringBootApplication
@CrossOrigin (origins = "*")
@RestController
@RequestMapping({"api"})
public class UserController {


    private static final String EXTERNAL_FILE_PATH = "/Users/mehreennajm/Desktop/profiles/";
    @Autowired
    private  UserService userService;




    //list all of Users
    @GetMapping(value = "/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<User> getListOfUsers() throws IOException {
        return new ResponseEntity(userService.getListOfUsers(), HttpStatus.OK);
    }

    @GetMapping({"/users/managers"})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    public List<UserData> getListOfOrdinaryUsers() throws IOException {return userService.
            getListOfOrdinaryUsers();}



    @PostMapping(value = {"/users/add-user"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void addNewUser(@RequestParam("firstName") String firstName,
                                           @RequestParam("lastName") String lastName,
                                           @RequestParam("email") String email,
                                           @RequestParam("password") String password,
                                           @RequestParam("role") UserRole role,
                                           @RequestParam("profilePhoto") MultipartFile profilePhoto) throws IOException {

        userService.addNewUser(firstName,lastName,email,password,role,profilePhoto);
    }



    // update User record
    @Transactional
    @PutMapping(value="/users/{id}/edit",consumes={MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE, MediaType.ALL_VALUE},
            produces={MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE, MediaType.ALL_VALUE})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<User> updateUserRecord(@PathVariable Long id,
                                                     @RequestParam(value = "profilePhoto") MultipartFile profilePhoto,
                                                     @RequestParam("firstName") String firstName,
                                                     @RequestParam("lastName") String lastName,
                                                     @RequestParam("email") String email,
                                                     @RequestParam("password") String password,
                                                     @RequestParam("role") UserRole role) throws IOException, ConfigurationException {



        userService.updateUser(id,profilePhoto,firstName,lastName,email,password,role);
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
