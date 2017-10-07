package com.crud.tasks.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

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