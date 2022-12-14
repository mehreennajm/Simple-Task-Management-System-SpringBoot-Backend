package com.example.task_mis.respositories;
import com.example.task_mis.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findUserByEmail(String email);

    @Query(value = "SELECT user_id , token_expiration_date FROM USERS", nativeQuery = true)
    List<User> findUserByExpireToken();

    @Query(value = "SELECT * FROM USERS u WHERE u.role = 'ROLE_USER'", nativeQuery = true)
    List<User> findAllUsers();


    @Query("SELECT u FROM User u WHERE u.email = ?1")
     User findByEmail(String email);

     User findByResetPasswordToken(String token);

    @Query(value = "SELECT reset_password_token FROM USERS", nativeQuery = true)
     Optional<User> getUserToken(String token);




}
