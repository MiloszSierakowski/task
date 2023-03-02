package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TaskMapper taskMapper;
    @MockBean
    private DbService service;

    @Test
    void getTasksTest() throws Exception {
        List<Task> taskList = List.of(new Task(1L, "test", "Testowy task"));
        List<TaskDto> taskDtoList = List.of(new TaskDto(1L, "test", "Testowy task"));

        when(service.getAllTasks()).thenReturn(taskList);
        when(taskMapper.mapToTaskDtoList(taskList)).thenReturn(taskDtoList);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is("test")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content", Matchers.is("Testowy task")));

    }

    @Test
    void getTaskTest() throws Exception {
        Optional<Task> task = Optional.of(new Task(1L, "test", "Testowy task"));
        TaskDto taskDto = new TaskDto(1L, "test", "Testowy task");

        when(service.findTaskById(1L)).thenReturn(task);
        when(taskMapper.mapToTaskDto(task.get())).thenReturn(taskDto);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks/kodilla/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("test")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Testowy task")));
    }

    @Test
    void getTaskKodilla() throws Exception {
        Task task = new Task(1L, "test", "Testowy task");
        TaskDto taskDto = new TaskDto(1L, "test", "Testowy task");

        when(service.getTask(1L)).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("test")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Testowy task")));
    }

    @Test
    void deleteTaskTest() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));

    }

    @Test
    void updateTaskTest() throws Exception {
        Task task = new Task(1L, "test", "Testowy task");
        TaskDto taskDto = new TaskDto(1L, "test", "Testowy task");

        when(taskMapper.mapToTask(any(TaskDto.class))).thenReturn(task);
        when(service.saveTask(any(Task.class))).thenReturn(task);
        when(taskMapper.mapToTaskDto(any(Task.class))).thenReturn(taskDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("test")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Testowy task")));

    }

    @Test
    void createTaskTest() throws Exception {
        Task task = new Task(1L, "test", "Testowy task");
        TaskDto taskDto = new TaskDto(1L, "test", "Testowy task");

        when(taskMapper.mapToTask(any(TaskDto.class))).thenReturn(task);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void checkNumberTest() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks/pierwszenstwo/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.is("To liczba pierwsza")));
    }

    @Test
    void getWelcomeMessageTest() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks/wiadomosc/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.is("No to prosze bardzo wiadomosc")));
    }

    @Test
    void updateMessageTest() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/tasks/wiadomosc/argument/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.is("zobaczymy numerek podany jako argument 1")));
    }
}