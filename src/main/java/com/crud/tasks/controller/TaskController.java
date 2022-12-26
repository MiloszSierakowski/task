package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<TaskDto>> getTasks() {
        List<Task> tasks = service.getAllTasks();
        return ResponseEntity.ok(taskMapper.mapToTaskDtoList(tasks));
    }

    @GetMapping(value = "{taskId}")
    public TaskDto getTask(@PathVariable Long taskId) {
        Optional<Task> searchedTask = service.findTaskById(taskId);
        return searchedTask.map(taskMapper::mapToTaskDto).orElse(null);
    }

    @GetMapping(value = "kodilla/{taskId}")
    public ResponseEntity<TaskDto> getTaskKodilla(@PathVariable Long taskId) throws TaskNotFoundException {
        return ResponseEntity.ok(taskMapper.mapToTaskDto(service.getTask(taskId)));
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteTask(Long taskId) {
        service.deleteTaskById(taskId);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<TaskDto> updateTask(@RequestBody TaskDto taskDto) {
        Task task = taskMapper.mapToTask(taskDto);
        Task savedTask = service.saveTask(task);
        return ResponseEntity.ok(taskMapper.mapToTaskDto(savedTask));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createTask(@RequestBody TaskDto taskDto) {
        Task task = taskMapper.mapToTask(taskDto);
        service.saveTask(task);
        return ResponseEntity.ok().build();
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
