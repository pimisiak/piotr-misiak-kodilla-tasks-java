package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import java.util.List;
import javax.persistence.NoResultException;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DbService {
    @Autowired
    private TaskRepository repository;

    public List<Task> getAllTasks() {
        return Lists.newArrayList(repository.findAll());
    }

    public Task getTaskById(final Long id) {
        return repository.findById(id).orElseThrow(NoResultException::new);
    }
}
