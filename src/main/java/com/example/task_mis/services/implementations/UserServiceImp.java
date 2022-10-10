package com.example.task_mis.services.implementations;
import com.example.task_mis.dto.UserData;
import com.example.task_mis.enums.UserRole;
import com.example.task_mis.errors.CustomError;
import com.example.task_mis.entities.User;
import com.example.task_mis.respositories.UserRepository;
import com.example.task_mis.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    LocalDateTime localDateTime = LocalDateTime.now();

    @Override
    public List < UserData > getListOfUsers() {
        List < UserData > userDataList = new ArrayList < > ();
        List < User > users = userRepository.findAll(Sort.by(Sort.Direction.DESC, "userId"));
        for (User user: users) {
            userDataList.add(convertUserToDto(user));
        }
        return userDataList;

    }

    @Override
    public List < UserData > getListOfOrdinaryUsers() {
        List < UserData > managerDataList = new ArrayList < > ();
        List < User > users = userRepository.findAllUsers();
        for (User user: users) {
            managerDataList.add(convertUserToDto(user));
        }
        return managerDataList;
    }

    @Override
    public void addNewUser(MultipartFile profilePhoto, String firstName, String lastName, String email, String password, UserRole userRole) throws IOException {

        User user = new User();
        user.setFirstName(firstName);

        user.setLastName(lastName);

        user.setEmail(email);

        String passwordEncode = this.passwordEncoder.encode(password);
        user.setPassword(passwordEncode);

        user.setRole(userRole);

        String fileName = localDateTime + StringUtils.cleanPath(profilePhoto.getOriginalFilename());
        user.setProfilePhoto(fileName);

        String FILE_DIR = "/Users/mehreennajm/Desktop/profiles";
        Files.copy(profilePhoto.getInputStream(), Paths.get(FILE_DIR + File.separator + fileName), StandardCopyOption.REPLACE_EXISTING);

        userRepository.save(user);
    }

    @Override
    public User updateUser(Long userId,
                           MultipartFile profilePhoto,
                           String firstName,
                           String lastName,
                           String email,
                           String password,
                           UserRole userRole) throws IOException {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalStateException(CustomError.ID_NOT_FOUND_ERROR));

        Path imagesPath = Paths.get(
                "/Users/mehreennajm/Desktop/profiles/" +
                        user.getProfilePhoto());

        if (Files.exists(imagesPath)) {
            Files.delete(imagesPath);
            String fileName = localDateTime + StringUtils.cleanPath(profilePhoto.getOriginalFilename());
            user.setProfilePhoto(fileName);

            String FILE_DIR = "/Users/mehreennajm/Desktop/profiles";
            Files.copy(profilePhoto.getInputStream(), Paths.get(FILE_DIR + File.separator + fileName), StandardCopyOption.REPLACE_EXISTING);

            user.setFirstName(firstName);

            user.setLastName(lastName);

            user.setEmail(email);

            String passwordEncode = this.passwordEncoder.encode(password);
            user.setPassword(passwordEncode);

        } else {

            user.setFirstName(firstName);

            user.setLastName(lastName);

            user.setEmail(email);

            String passwordEncode = this.passwordEncoder.encode(password);
            user.setPassword(passwordEncode);

        }
        user.setRole(userRole);
        userRepository.save(user);
        System.out.println("File " +
                imagesPath.toAbsolutePath().toString() +
                " successfully removed");

        return user;
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(CustomError.ID_NOT_FOUND_ERROR));
        Path imagesPath = Paths.get(
                "/Users/mehreennajm/Desktop/profiles/" +
                        user.getProfilePhoto());

        try {
            Files.delete(imagesPath);
            System.out.println("File " +
                    imagesPath.toAbsolutePath().toString() +
                    " successfully removed");
        } catch (IOException e) {
            System.err.println("Unable to delete " +
                    imagesPath.toAbsolutePath().toString() +
                    " due to...");
            e.printStackTrace();
        }

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
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setProfilePhoto(user.getProfilePhoto());
        userDto.setRole(user.getRole().toString());
        return userDto;
    }

    public void updateResetPasswordToken(String token, String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            userRepository.save(user);
        } else {
            throw new UsernameNotFoundException("Could not find any user with the email " + email);
        }
    }

    public User getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        user.setResetPasswordToken(null);
        userRepository.save(user);
    }

}