package com.example.task_mis.services.implementations;

import com.example.task_mis.dto.TaskData;
import com.example.task_mis.models.Task;
import com.example.task_mis.respositories.TaskRepository;
import com.example.task_mis.services.interfaces.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImp implements TaskService {
    private final TaskRepository taskRepository;

    public TaskServiceImp(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    @Override
    public List<TaskData> getListOfTasks() {
        return null;
    }

    @Override
    public void addNewTask(Task task) {

    }

    @Override
    public void updateTaskRecord(Long taskId, Task task) {

    }

    @Override
    public void deleteTask(Long taskId) {

    }

    @Override
    public Task getSpecificRecord(Long taskId) {
        return null;
    }

    @Override
    public List<?> searchTask(String title) {
        return null;
    }
}
