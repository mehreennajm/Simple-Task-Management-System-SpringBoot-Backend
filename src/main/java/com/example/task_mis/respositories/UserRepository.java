package com.example.task_mis.respositories;

import com.example.task_mis.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Long, User>{
}
