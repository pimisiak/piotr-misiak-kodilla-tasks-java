package com.crud.tasks.controller;

import com.crud.tasks.dto.TaskDto;
import com.crud.tasks.entity.Task;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.TaskService;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {
    @MockBean
    private TaskService taskService;
    @MockBean
    private TaskMapper taskMapper;
    @Autowired
    private MockMvc mockMvc;
    private Task task;
    private TaskDto taskDto;

    @Before
    public void initTestData() {
        task = new Task(1L, "Test Title", "Test Content");
        taskDto = new TaskDto(1L, "Test Title", "Test Content");
        when(taskMapper.mapToTask(any(TaskDto.class))).thenReturn(task);
        when(taskMapper.mapToTaskDto(any(Task.class))).thenReturn(taskDto);
    }

    @Test
    public void testGetTasks() throws Exception {
        //Given
        final List<Task> tasks = Collections.singletonList(task);
        final List<TaskDto> taskDtos = Collections.singletonList(taskDto);
        when(taskService.findAllTasks()).thenReturn(tasks);
        when(taskMapper.mapToTaskDtoList(anyList())).thenReturn(taskDtos);
        //When
        mockMvc.perform(get("/api/v1/tasks"))
                //Then
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Test Title"))
                .andExpect(jsonPath("$[0].content").value("Test Content"));
    }

    @Test
    public void testGetTask() throws Exception {
        //Given
        when(taskService.findTaskById(1L)).thenReturn(task);
        //When
        mockMvc.perform(get("/api/v1/tasks/{id}", 1))
                //Then
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.content").value("Test Content"));
    }

    @Test
    public void testCreateTask() throws Exception {
        //Given
        final Gson gson = new Gson();
        final String jsonContent = gson.toJson(taskDto);
        //When
        mockMvc.perform(post("/api/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonContent))
                //Then
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateTask() throws Exception {
        //Given
        final Gson gson = new Gson();
        final String jsonContent = gson.toJson(taskDto);
        when(taskService.updateTask(anyLong(), any(Task.class))).thenReturn(task);
        //When
        mockMvc.perform(put("/api/v1/tasks/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonContent))
                //Then
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.content").value("Test Content"));
    }

    @Test
    public void testDeleteTask() throws Exception {
        //Given
        //taskController
        //When
        mockMvc.perform(delete("/api/v1/tasks/{id}", 1))
                //Then
                .andExpect(status().isOk());
    }
}
