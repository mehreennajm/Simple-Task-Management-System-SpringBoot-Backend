package com.example.task_mis.services.implementations;

import com.example.task_mis.dto.TaskData;
import com.example.task_mis.errors.CustomError;
import com.example.task_mis.models.Task;
import com.example.task_mis.respositories.TaskRepository;
import com.example.task_mis.services.interfaces.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImp implements TaskService {
    private final TaskRepository taskRepository;

    public TaskServiceImp(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    @Override
    public List<TaskData> getListOfTasks() {
        List <TaskData> taskDataList = new ArrayList<>();
        List <Task> tasks = taskRepository.findAll(Sort.by(Sort.Direction.DESC, "taskId"));
        for (Task task: tasks) {
            taskDataList.add(convertTaskToDto(task));
        }
        return taskDataList;
    }

    @Override
    public void addNewTask(Task task) {
        taskRepository.save(task);
    }

    @Override
    public Task updateTask(Long taskId, Task taskRequest) {
        Task task = taskRepository.findById(taskId).orElseThrow(() ->
                new IllegalStateException(CustomError.ID_NOT_FOUND_ERROR));
        task.setTitle(taskRequest.getTitle());
        task.setCreateDate(taskRequest.getCreateDate());
        task.setDueDate(taskRequest.getDueDate());
        task.setDescription(taskRequest.getDescription());
        task.setStatus(taskRequest.getStatus());
        task.setUsers(taskRequest.getUsers());
        this.taskRepository.save(task);
        return task;
    }

    @Override
    public void deleteTask(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() ->
                new IllegalStateException(CustomError.ID_NOT_FOUND_ERROR));
        this.taskRepository.delete(task);
    }

    @Override
    public Task getSpecificTaskRecord(Long taskId) {
        Optional <Task> taskOptional = taskRepository.findById(taskId);
        Task newTask = null;
        if (taskOptional.isPresent()) {
            newTask = taskOptional.get();
        } else {
            throw new IllegalStateException(CustomError.ID_NOT_FOUND_ERROR);
        }
        return newTask;
    }

    @Autowired
    private ModelMapper modelMapper;
    private TaskData convertTaskToDto(Task task) {
        TaskData taskDto = modelMapper.map(task, TaskData.class);
        taskDto.setTaskId(task.getTaskId());
        taskDto.setTitle(task.getTitle());
        taskDto.setCreateDate(task.getCreateDate());
        taskDto.setDueDate(task.getDueDate());
        taskDto.setStatus(task.getStatus());
        taskDto.setUser(task.getUsers().getFirstName() + ' ' + task.getUsers().getLastName());
        taskDto.setDescription(task.getDescription());
        return taskDto;
    }
    


}
