package com.example.task_mis.respositories;

import com.example.task_mis.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Long, Task> {
}
