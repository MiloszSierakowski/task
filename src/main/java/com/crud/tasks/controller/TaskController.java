package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/tasks")
public class TaskController {

    @GetMapping
    public List<TaskDto> getTasks() {
        return new ArrayList<>();
    }

    @GetMapping(value = "{taskId}")
    public TaskDto getTask(@PathVariable Long taskId) {
        return new TaskDto(1L, "test title", "test_content");
    }

    @DeleteMapping
    public void deleteTask(Long taskId) {

    }

    @PutMapping
    public TaskDto updateTask(TaskDto taskDto) {
        return new TaskDto(1L, "test title", "test_content");
    }

    @PostMapping
    public void createTask(TaskDto taskDto) {

    }

    @GetMapping(value = "wiadomosc/{number}")
    public String getWelcomeMessage(@PathVariable int number){
        return "No to prosze bardzo wiadomosc";
    }

    @PutMapping(value = "wiadomosc/argument/{number}")
    public String updateMessage(@PathVariable int number){
        return "zobaczymy numerek podany jako argument " + number;
    }

}
