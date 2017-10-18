package com.crud.tasks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class TasksApplication extends SpringBootServletInitializer {

    public static void main(final String[] args) {
        SpringApplication.run(TasksApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder builder) {
        return builder.sources(TasksApplication.class);
    }
}
