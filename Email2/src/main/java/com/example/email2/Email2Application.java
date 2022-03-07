package com.example.email2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@SpringBootApplication
public class Email2Application {
    public static final String HOST_NAME = "smtp.gmail.com";
    public static final Integer PORT = 587;
    public static final String USERNAME = "emailapi302@gmail.com";
    public static final String PASSWORD = "revature123";
    public static final String PROTOCOL = "mail.transport.protocol";
    public static final String SMTP = "smtp";
    public static final String AUTH = "mail.smtp.auth";
    public static final String STARTTLS = "mail.smtp.starttls.enable";
    public static final String DEBUG = "mail.debug";

    public static void main(String[] args) {
        SpringApplication.run(Email2Application.class, args);
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(HOST_NAME);
        mailSender.setPort(PORT);

        mailSender.setUsername(USERNAME);
        mailSender.setPassword(PASSWORD);

        Properties props = mailSender.getJavaMailProperties();
        props.put(PROTOCOL, SMTP);
        props.put(AUTH, "true");
        props.put(STARTTLS, "true");
        props.put(DEBUG, "true");

        return mailSender;
    }

}
