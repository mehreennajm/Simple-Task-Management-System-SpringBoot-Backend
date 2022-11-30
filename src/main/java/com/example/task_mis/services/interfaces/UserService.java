package com.example.task_mis.services.interfaces;
import com.example.task_mis.dto.UserData;
import com.example.task_mis.entities.User;
import com.example.task_mis.enums.UserRole;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public interface UserService {
    List<UserData> getListOfUsers() throws IOException;
    List<UserData> getListOfOrdinaryUsers() throws IOException;
    void addNewUser(String firstName, String LastName, String email, String password, UserRole role,MultipartFile file) throws IOException;
    User updateUser(Long userId,MultipartFile profilePhoto, String firstName, String lastName, String email,  UserRole role) throws IOException;
    void deleteUser(Long userId);
    User getSpecificUserRecord(Long id);
    void updateResetPasswordToken(String token, String email);
    User getByResetPasswordToken(String token);
    void updatePassword(User customer, String newPassword);

    String getImage(String imageName) throws IOException;
}
