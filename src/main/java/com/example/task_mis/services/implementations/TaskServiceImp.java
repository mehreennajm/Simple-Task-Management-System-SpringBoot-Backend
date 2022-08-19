package com.example.task_mis.services.implementations;

import com.example.task_mis.dto.TaskData;
import com.example.task_mis.dto.UserData;
import com.example.task_mis.errors.CustomError;
import com.example.task_mis.models.Task;
import com.example.task_mis.models.User;
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
        Optional<Task> taskOptional = taskRepository.findTaskById(task.getTaskId());
        if(taskOptional.isPresent()){
            throw new IllegalStateException(CustomError.USER_NAME_ALREADY_EXIST);
        }
        taskRepository.save(task);
    }

    @Override
    public Task updateTask(Long taskId, Task taskRequest) {
        Task task = taskRepository.findById(taskId).orElseThrow(() ->
                new IllegalStateException(CustomError.ID_NOT_FOUND_ERROR));
        task.setTitle(taskRequest.getTitle());
        task.setCreateDate(taskRequest.getCreateDate());
        task.setDescription(taskRequest.getDescription());
        task.setDueDate(taskRequest.getDueDate());
        this.taskRepository.save(task);
        return task;
    }

    @Override
    public Task updateTaskRecord(Long taskId, Task task) {
        return null;
        }

    @Override
    public void deleteTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalStateException(CustomError.ID_NOT_FOUND_ERROR));

        taskRepository.delete(task);
        
    }


    @Override
    public Task getSpecificTaskRecord(Long id) {
        Optional < Task > taskOptional = taskRepository.findById(id);
        Task newUser = null;
        if (taskOptional.isPresent()) {
            newUser = taskOptional.get();
        } else {
            throw new IllegalStateException(CustomError.ID_NOT_FOUND_ERROR);
        }
        return new Task();
    }

    @Autowired
    private ModelMapper modelMapper;
    private TaskData convertTaskToDto(Task task) {
        TaskData taskDto = modelMapper.map(task, TaskData.class);
        taskDto.setTaskId(task.getTaskId());
        taskDto.setCreateDate(task.getCreateDate());
        taskDto.setDescription(task.getDescription().toString());
        taskDto.setDueDate(task.getDueDate());
        return taskDto;
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
