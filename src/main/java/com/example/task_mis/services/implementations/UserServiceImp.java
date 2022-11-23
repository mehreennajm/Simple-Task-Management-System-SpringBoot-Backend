package com.example.task_mis.services.implementations;
import com.example.task_mis.dto.UserData;
import com.example.task_mis.entities.FileUploadUtil;
import com.example.task_mis.enums.UserRole;
import com.example.task_mis.errors.CustomError;
import com.example.task_mis.entities.User;
import com.example.task_mis.respositories.UserRepository;
import com.example.task_mis.services.interfaces.UserService;
import net.bytebuddy.utility.RandomString;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.List;

@Service
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }
    @Override
    public List<UserData> getListOfUsers() throws IOException {
        List < UserData > userDataList = new ArrayList <> ();
        List <User> users = userRepository.findAll(Sort.by(Sort.Direction.DESC, "userId"));
        for (User user: users) {
            File image = new File("user-photos/"+ user.getProfilePhoto());
            String encodeUrl = Base64.getEncoder().withoutPadding().encodeToString(Files.readAllBytes(image.toPath()));
            user.setProfilePhoto(encodeUrl);
            userDataList.add(convertUserToDto(user));
        }
        return userDataList;

    }

    @Override
    public String getImage(String imageName) throws IOException {
        File image = new File("user-photos/"+ imageName);
        return Base64.getEncoder().withoutPadding().encodeToString(Files.readAllBytes(image.toPath()));
    }
    @Override
    public List<UserData> getListOfOrdinaryUsers(){
        List <UserData> managerDataList = new ArrayList <> ();
        List <User> users = userRepository.findAllUsers();
        for (User user: users) {
            managerDataList.add(convertUserToDto(user));
        }
        return managerDataList;
    }


    @Override
    public void addNewUser(String firstName,String lastName,String email,String password, UserRole role, MultipartFile profilePhoto) throws IOException {

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        String passwordEncode = this.passwordEncoder.encode(password);
        user.setPassword(passwordEncode);
        user.setRole(role);

        //Storing file in Database and the Directory
        String fileName = new RandomString(10) +  StringUtils.cleanPath(profilePhoto.getOriginalFilename());
        user.setProfilePhoto(fileName);
        String FILE_DIR = "user-photos/";
        FileUploadUtil.saveFile(FILE_DIR, fileName, profilePhoto);

        userRepository.save(user);
    }

    @Override
    public User updateUser(Long userId, @NotNull MultipartFile profilePhoto , String firstName, String lastName, String email, String password, UserRole role) throws IOException {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalStateException(CustomError.ID_NOT_FOUND_ERROR));


            Path imagesPath = Paths.get("user-photos/" + user.getProfilePhoto());
            Files.delete(imagesPath);
            String fileName = RandomString.make(10) +StringUtils.cleanPath(profilePhoto.getOriginalFilename());
            user.setProfilePhoto(fileName);
            String FILE_DIR = "user-photos/";
            Files.copy(profilePhoto.getInputStream(), Paths.get(FILE_DIR + File.separator + fileName), StandardCopyOption.REPLACE_EXISTING);

            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            String passwordEncode = this.passwordEncoder.encode(password);
            user.setPassword(passwordEncode);
            userRepository.save(user);
            return user;
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(CustomError.ID_NOT_FOUND_ERROR));
        Path imagesPath = Paths.get("user-photos/" +
                        user.getProfilePhoto());

        try {
            Files.delete(imagesPath);
            System.out.println("File " +
                    imagesPath.toAbsolutePath() +
                    " successfully removed");
        } catch (IOException e) {
            System.err.println("Unable to delete " +
                    imagesPath.toAbsolutePath() +
                    " due to...");
            e.printStackTrace();
        }

        userRepository.delete(user);
    }

    @Override
    public User getSpecificUserRecord(Long id) {
        Optional < User > userOptional = userRepository.findById(id);

        User newUser;
        if (userOptional.isPresent()) {
            newUser = userOptional.get();
        } else {
            throw new IllegalStateException(CustomError.ID_NOT_FOUND_ERROR);
        }
        return newUser;
    }

    private final ModelMapper modelMapper;
    private UserData convertUserToDto(User user){
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