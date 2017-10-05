package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.NoResultException;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {
    @Autowired
    private DbService service;
    @Autowired
    private TaskMapper taskMapper;

    @RequestMapping(method = RequestMethod.GET)
    public List<TaskDto> getTasks() {
        return taskMapper.mapToTaskDtoList(service.getAllTasks());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public TaskDto getTask(@PathVariable("id") final long taskId) {
        return taskMapper.mapToTaskDto(service.getTaskById(taskId));
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

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoResultException.class)
    public void handleNoResultException() {
    }
}
