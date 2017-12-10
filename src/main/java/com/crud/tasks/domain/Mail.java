package com.crud.tasks.domain;

public interface Mail {
    String getMailTo();
    String getSubject();
    String getMessage();
    String getToCc();
}
