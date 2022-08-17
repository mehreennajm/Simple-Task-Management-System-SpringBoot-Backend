package com.example.task_mis.services.implementations;
import com.example.task_mis.dto.UserData;
import com.example.task_mis.models.User;
import com.example.task_mis.respositories.UserRepository;
import com.example.task_mis.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;

    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserData> getListOfUsers() {
        List <UserData> userDataList = new ArrayList<>();
        List <User> users = userRepository.findAll();
        for (User user: users) {
            userDataList.add(convertUserToDto(user));
        }
        return userDataList;

    }

    @Override
    public void addNewUser(User user) {

    }

    @Override
    public void updateUser(Long userId, User user) {

    }

    @Override
    public void deleteUser(Long userId) {

    }

    @Override
    public User getSpecificUserRecord(Long id) {
        return null;
    }

    @Autowired
    private ModelMapper modelMapper;
    private UserData convertUserToDto(User user) {
        UserData userDto = modelMapper.map(user, UserData.class);
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setRole(user.getRole().toString());
        return userDto;
    }
}
