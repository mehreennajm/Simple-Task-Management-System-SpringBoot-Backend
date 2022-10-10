package com.example.task_mis.services.interfaces;
import com.example.task_mis.dto.UserData;
import com.example.task_mis.entities.User;
import com.example.task_mis.enums.UserRole;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    List<UserData> getListOfUsers();
    List<UserData> getListOfOrdinaryUsers();
    void addNewUser(MultipartFile file, String firstName, String LastName, String email, String password
    , UserRole userRole) throws IOException;
    User updateUser(Long userId, MultipartFile profilePhoto, String firstName, String lastName, String email, String password
            , UserRole userRole) throws IOException;
    void deleteUser(Long userId);
    User getSpecificUserRecord(Long id);
    void updateResetPasswordToken(String token, String email);
    User getByResetPasswordToken(String token);
    void updatePassword(User customer, String newPassword);


}
