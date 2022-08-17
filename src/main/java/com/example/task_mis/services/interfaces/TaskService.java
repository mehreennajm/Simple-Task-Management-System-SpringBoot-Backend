package com.example.task_mis.services.interfaces;
import com.example.task_mis.dto.TaskData;
import com.example.task_mis.models.Task;


import java.util.List;

public interface TaskService {
    List<TaskData> getListOfTasks();
    void addNewTask(Task task);
    void updateTaskRecord(Long taskId, Task task);
    void deleteTask(Long taskId);
    Task getSpecificRecord(Long taskId);
    List<?> searchTask(String title);
}
