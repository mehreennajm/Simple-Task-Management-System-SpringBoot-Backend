package com.example.task_mis.services.implementations;
import com.example.task_mis.dto.UserData;
import com.example.task_mis.errors.CustomError;
import com.example.task_mis.entities.User;
import com.example.task_mis.respositories.UserRepository;
import com.example.task_mis.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserData> getListOfUsers() {
        List <UserData> userDataList = new ArrayList<>();
        List <User> users = userRepository.findAll(Sort.by(Sort.Direction.DESC, "userId"));
        for (User user: users) {
            userDataList.add(convertUserToDto(user));
        }
        return userDataList;

    }

    @Override
    public void addNewUser(User user) {
        Optional userOptional = userRepository.findUserByFirstName(user.getUsername());
        if(userOptional.isPresent()){
            throw new IllegalStateException(CustomError.USER_NAME_ALREADY_EXIST);
        }
        String passwordEncode = this.passwordEncoder.encode(user.getPassword());
        user.setRole(user.getRole());
        user.setPassword(passwordEncode);
        userRepository.save(user);
    }

    @Override
    public User updateUser(Long userId, User userRequest) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalStateException(CustomError.ID_NOT_FOUND_ERROR));
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setRole(userRequest.getRole());
        this.userRepository.save(user);
        return user;
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(CustomError.ID_NOT_FOUND_ERROR));

        userRepository.delete(user);
    }

    @Override
    public User getSpecificUserRecord(Long id) {
        Optional < User > userOptional = userRepository.findById(id);
        User newUser = null;
        if (userOptional.isPresent()) {
            newUser = userOptional.get();
        } else {
            throw new IllegalStateException(CustomError.ID_NOT_FOUND_ERROR);
        }
        return newUser;
    }

    @Autowired
    private ModelMapper modelMapper;
    private UserData convertUserToDto(User user) {
        UserData userDto = modelMapper.map(user, UserData.class);
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setRole(user.getRole().toString());
        return userDto;
    }
}
