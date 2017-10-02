package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    @RequestMapping(method = RequestMethod.GET)
    public List<TaskDto> getTasks() {
        return new ArrayList<>();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public TaskDto getTask(@PathVariable("id") final long taskId) {
        return new TaskDto((long)1, "test title", "test_content");
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public void deleteTask(@PathVariable("id") final long taskId) {
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public TaskDto updateTask(@PathVariable("id") final long taskId, @RequestBody final TaskDto taskDto) {
        return new TaskDto((long)1, "Edited test title", "Test content");
    }

    @RequestMapping(method = RequestMethod.POST)
    public void createTask(@RequestBody final TaskDto taskDto) {
    }
}
