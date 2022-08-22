package com.example.task_mis.respositories;

import com.example.task_mis.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    Optional<User> findUserByFirstName(String firstName);
    User findUserByUsername(String userName);
}
