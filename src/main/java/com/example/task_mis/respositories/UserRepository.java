package com.example.task_mis.respositories;

import com.example.task_mis.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findUserByFirstName(String firstName);
    User findUserByUsername(String userName);

    @Query(value = "SELECT * FROM USERS u WHERE u.role = 'ROLE_USER'", nativeQuery = true)
    List<User> findAllUsers();

}
