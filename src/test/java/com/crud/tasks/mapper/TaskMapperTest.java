package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskMapperTest {
    @Autowired
    private TaskMapper taskMapper;

    @Test
    void mapToTask() {
        TaskDto dto = new TaskDto(1L, "test", "kontent");

        Task task = taskMapper.mapToTask(dto);

        assertAll(
                () -> assertEquals(dto.getId(), task.getId()),
                () -> assertEquals(dto.getTitle(), task.getTitle()),
                () -> assertEquals(dto.getContent(), task.getContent())
        );
    }

    @Test
    void mapToTaskDto() {
        Task task = new Task(1L, "test", "kontent");

        TaskDto dto = taskMapper.mapToTaskDto(task);

        assertAll(
                () -> assertEquals(task.getId(), dto.getId()),
                () -> assertEquals(task.getTitle(), dto.getTitle()),
                () -> assertEquals(task.getContent(), dto.getContent())
        );
    }

    @Test
    void mapToTaskDtoList() {

        List<Task> taskList = List.of(new Task(1L, "test1", "kontekst"));

        List<TaskDto> taskDtos = taskMapper.mapToTaskDtoList(taskList);

        assertAll(
                () -> assertEquals(taskList.size(), taskDtos.size()),
                () -> assertEquals(taskList.get(0).getId(), taskDtos.get(0).getId()),
                () -> assertEquals(taskList.get(0).getTitle(), taskDtos.get(0).getTitle()),
                () -> assertEquals(taskList.get(0).getContent(), taskDtos.get(0).getContent())
        );

    }
}