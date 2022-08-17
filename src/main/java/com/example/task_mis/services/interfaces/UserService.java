package com.example.task_mis.services.interfaces;
import com.example.task_mis.dto.UserData;
import com.example.task_mis.models.User;
import java.util.List;

public interface UserService {
    List<UserData> getListOfUsers();
    void addNewUser(User user);
    void updateUser(Long userId, User user);
    void deleteUser(Long userId);
    User getSpecificUserRecord(Long id);
}
