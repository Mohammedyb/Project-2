package com.example.email2.Controllers;

import com.example.email2.DTO.EmailDTO;
import com.example.email2.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("email")
public class EmailController {
    private EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<?> sendEmail(@RequestBody List<String> list){

        emailService.sendEmail(new EmailDTO("project02sender@gmail.com", list.get(0),  list.get(1),  list.get(2)));

        return ResponseEntity.accepted().build();
    }
}