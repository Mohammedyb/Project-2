package com.example.email2.Controllers;

import com.example.email2.DTO.EmailDTO;
import com.example.email2.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("email")
public class EmailController {
    private final EmailService emailService;


    @Autowired
    public EmailController(EmailService emailService){
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<?> sendEmail(@RequestBody String list){

        emailService.sendEmail(new EmailDTO("project02sender@gmail.com", "mohammed.bubshait@revature.com", "emailtest", "Just Testing"));

        return ResponseEntity.accepted().build();
    }
}