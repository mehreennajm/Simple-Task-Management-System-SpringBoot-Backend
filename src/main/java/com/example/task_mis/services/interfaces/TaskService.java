package com.example.task_mis.services.interfaces;
import com.example.task_mis.dto.TaskData;
import com.example.task_mis.models.Task;


import java.util.List;

public interface TaskService {
    List<TaskData> getListOfTasks();
    void addNewTask(Task task);
    void deleteTask(Long taskId);
    Task getSpecificTaskRecord(Long taskID);
    Task updateTask(Long id, Task taskRequest);
}
