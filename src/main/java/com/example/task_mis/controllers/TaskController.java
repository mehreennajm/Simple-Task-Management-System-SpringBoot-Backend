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
@RequestMapping({"api"})
public class TaskController {
    @Autowired
    private  TaskService taskService;

    //list all of tasks
    @GetMapping({"/tasks"})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    public List<TaskData> getListOfTasks(){return taskService.getListOfTasks();}


    @PostMapping({"/tasks/add-task"})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    public void addNewTask(@RequestBody Task task){taskService.addNewTask(task);}

    // update Project record
    @Transactional
    @PutMapping({"/tasks/{id}/edit"})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    public Task updateTaskRecord(@PathVariable("id") Long taskId ,
                                 @RequestBody Task task){
        taskService.updateTask(taskId,task);
        return task;
    }

    //getting a specific Task  via id
    @GetMapping({"/tasks/{id}"})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    public Task getSpecificRecord(@PathVariable(value = "id") Long taskId){
        Task task = taskService.getSpecificTaskRecord(taskId);
        return task;
    }

    //delete task record
    @DeleteMapping({ "/tasks/{id}/delete"})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    public void deleteTask(@PathVariable("id") Long taskId){
        taskService.deleteTask(taskId);
    }

}
