package com.example.task_mis.dto;

import lombok.Data;


@Data
public class UserData {
    private Long userId;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String role;

}
