package com.example.task_mis.controllers;
import com.example.task_mis.dto.TaskData;
import com.example.task_mis.models.Task;
import com.example.task_mis.services.interfaces.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "api/tasks")
public class TaskController {
    @Autowired
    private ModelMapper modelMapper;
    private final TaskService taskService;
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    //list all of tasks
    @GetMapping
    public List<TaskData> getListOfTasks(){return taskService.getListOfTasks();}

    @PostMapping(path = "/add-task")
    public void addNewTask(@RequestBody Task task){taskService.addNewTask(task);}

    //update task record
    @Transactional
    @PutMapping("/{id}/edit")
    public ResponseEntity<TaskData> updateTaskRecord(@PathVariable Long id, @RequestBody TaskData taskData) {

        //convert DTO to Entity
        Task taskRequest = modelMapper.map(taskData, Task.class);
        Task newTask = taskService.updateTask(id, taskRequest);

        // entity to DTO
        TaskData taskResponse = modelMapper.map(newTask, TaskData.class);
        return ResponseEntity.ok().body(taskResponse);
    }

    //getting a specific Task  via id
    @GetMapping("/{id}")
    public Task getSpecificRecord(@PathVariable(value = "id") Long taskId){
        Task task = taskService.getSpecificTaskRecord(taskId);
        return task;
    }

    //delete task record
    @DeleteMapping(path = "/{id}/delete")
    public void deleteTask(@PathVariable("id") Long taskId){
        taskService.deleteTask(taskId);
    }

}
