package com.example.email2.Model;


import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Email {
    private String mailFrom;
    private String mailTo;
    private String mailSubject;
    private String mailContent;
    private String contentType;

    public Email() {
        contentType = "text/plain";
    }
}