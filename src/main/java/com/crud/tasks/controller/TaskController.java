package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final DbService service;
    private final TaskMapper taskMapper;

    @GetMapping
    public List<TaskDto> getTasks() {
        List<Task> tasks = service.getAllTasks();
        return taskMapper.mapToTaskDtoList(tasks);
    }

    @GetMapping(value = "{taskId}")
    public TaskDto getTask(@PathVariable Long taskId) {
        Optional<Task> searchedTask = service.findTaskById(taskId);
        return searchedTask.map(taskMapper::mapToTaskDto).orElse(null);
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
    public String getWelcomeMessage(@PathVariable int number) {
        return "No to prosze bardzo wiadomosc";
    }

    @PutMapping(value = "wiadomosc/argument/{number}")
    public String updateMessage(@PathVariable int number) {
        return "zobaczymy numerek podany jako argument " + number;
    }

}
