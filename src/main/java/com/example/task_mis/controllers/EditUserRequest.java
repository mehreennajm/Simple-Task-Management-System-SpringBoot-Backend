package com.example.task_mis.controllers;
import com.example.task_mis.enums.UserRole;
import lombok.*;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditUserRequest {
    public Long userId;
    public  String firstName;
    public  String lastName;
    public  String email;
    public UserRole role;
    @Nullable
    public MultipartFile profilePhoto;

}
