package com.crud.tasks.controller;

import com.crud.tasks.dto.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.TaskService;

import com.google.common.base.Preconditions;

import io.swagger.annotations.Api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping("/api/v1/tasks")
@CrossOrigin(origins = "*")
public class TaskController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskMapper taskMapper;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TaskDto> getTasks() {
        return taskMapper.mapToTaskDtoList(taskService.findAllTasks());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TaskDto getTask(@PathVariable("id") final long taskId) {
        return taskMapper.mapToTaskDto(taskService.findTaskById(taskId));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public void deleteTask(@PathVariable("id") final long taskId) {
        taskService.deleteTaskById(taskId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public TaskDto updateTask(@PathVariable("id") final long taskId, @RequestBody final TaskDto taskDto) {
        Preconditions.checkNotNull(taskDto);
        return taskMapper.mapToTaskDto(taskService.updateTask(taskId, taskMapper.mapToTask(taskDto)));
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createTask(@RequestBody final TaskDto taskDto) {
        Preconditions.checkNotNull(taskDto);
        taskService.saveTask(taskMapper.mapToTask(taskDto));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TaskNotFoundException.class)
    public void handleTaskNotFoundException() {
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NullPointerException.class)
    public void handleNullPointerException() {
    }
}
