package com.example.task_mis.controllers;
import com.example.task_mis.dto.TaskData;
import com.example.task_mis.entities.Task;
import com.example.task_mis.services.interfaces.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootApplication
@CrossOrigin (origins = "http://localhost:4200/")
@RestController
@RequestMapping(path = "api/tasks")
public class TaskController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private  TaskService taskService;

    //list all of tasks
    @GetMapping
    public List<TaskData> getListOfTasks(){return taskService.getListOfTasks();}

    @PostMapping(path = "/add-task")

    public void addNewTask(@RequestBody Task task){taskService.addNewTask(task);}

    // update Project record
    @Transactional
    @PutMapping(path ="/{id}/edit")

    public Task updateTaskRecord(@PathVariable("id") Long taskId ,
                                 @RequestBody Task task){
        taskService.updateTask(taskId,task);
        return task;
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
