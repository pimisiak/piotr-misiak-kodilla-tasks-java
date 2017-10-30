package com.crud.tasks.service;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    @Autowired
    private TaskRepository repository;

    public List<Task> findAllTasks() {
        return repository.findAll();
    }

    public Task findTaskById(final Long id) {
        return repository.findById(id).orElseThrow(TaskNotFoundException::new);
    }

    public Task saveTask(final Task task) {
        return repository.save(task);
    }

    @Transactional
    public Task updateTask(final Long id, final Task task) {
        final Task updatedTask = findTaskById(id);
        updatedTask.setTitle(task.getTitle());
        updatedTask.setContent(task.getContent());
        return updatedTask;
    }

    public void deleteTaskById(final Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new TaskNotFoundException();
    }
}
