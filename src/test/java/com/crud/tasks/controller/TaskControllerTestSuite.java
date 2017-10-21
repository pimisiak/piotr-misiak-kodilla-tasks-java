package com.crud.tasks.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.crud.tasks.TasksApplication;
import com.crud.tasks.domain.Task;
import com.crud.tasks.service.DbService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = TasksApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties"
)
public class TaskControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private DbService dbService;

    @Before
    public void beforeTests() {
        final Task task = new Task(1L, "Test task title", "Test task content");
        dbService.saveTask(task);
    }

    @Test
    public void testGetTasks() throws Exception {
        //Given
        //taskController
        //When
        mockMvc.perform(get("/api/v1/tasks"))
                //Then
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetTask() throws Exception {
        //Given
        //taskController
        //When
        mockMvc.perform(get("/api/v1/tasks/{id}", 1))
                //Then
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateTask() throws Exception {
        //Given
        //taskController
        //When
        mockMvc.perform(post("/api/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"id\":1,\"title\":\"Edited test title\",\"content\":\"Test content\"}"))
                //Then
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateTask() throws Exception {
        //Given
        //taskController
        //When
        mockMvc.perform(put("/api/v1/tasks/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"id\":1,\"title\":\"Edited test title\",\"content\":\"Test content\"}"))
                //Then
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
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