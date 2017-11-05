package com.crud.tasks.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder(builderMethodName = "privateBuilder")
@ToString
public class Mail {
    private String mailTo;
    private String subject;
    private String message;
    private String toCc;
    private String toBcc;

    public static MailBuilder builder(final String mailTo) {
        return privateBuilder().mailTo(mailTo);
    }
}
