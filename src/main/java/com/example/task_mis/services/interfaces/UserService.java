package com.example.task_mis.services.interfaces;
import com.example.task_mis.dto.UserData;
import com.example.task_mis.entities.User;
import java.util.List;

public interface UserService {
    List<UserData> getListOfUsers();
    void addNewUser(User user);
    User updateUser(Long userId, User userRequest);
    void deleteUser(Long userId);
    User getSpecificUserRecord(Long id);
}
