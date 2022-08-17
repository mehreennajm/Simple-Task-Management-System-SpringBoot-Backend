package com.example.task_mis.dto;

import com.example.task_mis.enums.TaskStatus;
import com.example.task_mis.models.User;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskData {
    private Long taskId;
    private String title;
    private String description;
    private LocalDate createDate;
    private LocalDate dueDate;
    private TaskStatus status;
    private String user;

}
