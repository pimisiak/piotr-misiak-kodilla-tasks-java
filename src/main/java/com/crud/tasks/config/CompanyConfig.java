package com.crud.tasks.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "info.company")
public class CompanyConfig {
    private String name;
    private String goal;
    private String email;
    private String phone;

    @Override
    public String toString() {
        return String.format("%s | %s | %s", name, email, phone);
    }
}
