package com.crud.tasks.service;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DbServiceTest {

    @Autowired
    private DbService dbService;

    @Test
    void getAllTasks() {
        List<Task> taskList = List.of(
                new Task(1L, "test1", "Testowy task1"),
                new Task(2L, "test2", "Testowy task2"),
                new Task(3L, "test3", "Testowy task3")
        );

        List<Task> savedTask = List.of(
                dbService.saveTask(taskList.get(0)),
                dbService.saveTask(taskList.get(1)),
                dbService.saveTask(taskList.get(2))
        );

        List<Task> allTasks = dbService.getAllTasks();

        try {
            assertAll(
                    () -> assertEquals(savedTask.size(), allTasks.size()),
                    () -> assertEquals(savedTask.get(0).getTitle(), allTasks.get(0).getTitle()),
                    () -> assertEquals(savedTask.get(1).getTitle(), allTasks.get(1).getTitle()),
                    () -> assertEquals(savedTask.get(2).getTitle(), allTasks.get(2).getTitle()),
                    () -> assertEquals(savedTask.get(0).getContent(), allTasks.get(0).getContent()),
                    () -> assertEquals(savedTask.get(1).getContent(), allTasks.get(1).getContent()),
                    () -> assertEquals(savedTask.get(2).getContent(), allTasks.get(2).getContent())
            );
        } finally {
            allTasks.forEach(
                    task -> dbService.deleteTaskById(task.getId())
            );
        }


    }

    @Test
    void findTaskById() throws TaskNotFoundException {
        Task task = new Task(1L, "task", "Tset task");
        Task saveTask = dbService.saveTask(task);
        Optional<Task> searchedTask = dbService.findTaskById(saveTask.getId());

        if (searchedTask.isPresent()) {
            assertEquals(saveTask.getTitle(), searchedTask.get().getTitle());
            assertEquals(saveTask.getContent(), searchedTask.get().getContent());
            dbService.deleteTaskById(searchedTask.get().getId());
        } else throw new TaskNotFoundException();
    }

    @Test
    void getTask() throws TaskNotFoundException {
        Task task = new Task(1L, "task", "Tset task");
        Task saveTask = dbService.saveTask(task);

        Task searchedTask = dbService.getTask(saveTask.getId());
        assertEquals(saveTask.getTitle(), searchedTask.getTitle());
        assertEquals(saveTask.getContent(), searchedTask.getContent());
        dbService.deleteTaskById(searchedTask.getId());
    }

    @Test
    void saveTask() throws TaskNotFoundException {
        Task task = new Task(1L, "test", "Testowy task");
        Task savedTask = dbService.saveTask(task);

        Optional<Task> findTask = dbService.findTaskById(savedTask.getId());

        if (findTask.isPresent()) {
            assertEquals(savedTask.getId(), findTask.get().getId());
            assertEquals("test", findTask.get().getTitle());
            assertEquals("Testowy task", findTask.get().getContent());

            dbService.deleteTaskById(savedTask.getId());
        } else throw new TaskNotFoundException();
    }

    @Test
    void deleteTaskById() {
        Task task = new Task(1L, "test", "Task testowy");
        Task savedTask = dbService.saveTask(task);

        dbService.deleteTaskById(savedTask.getId());

        assertThrows(TaskNotFoundException.class,
                ()-> dbService.getTask(savedTask.getId())
        );

    }
}