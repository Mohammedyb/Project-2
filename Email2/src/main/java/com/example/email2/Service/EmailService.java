package com.example.email2.Service;

import com.example.email2.DTO.EmailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service("mailService")
public class EmailService {
    private JavaMailSenderImpl emailSender;

    public EmailService() {
    }

    @Autowired
    public EmailService(JavaMailSenderImpl javaMailSender) {
        this.emailSender = javaMailSender;
    }

    public void sendEmail(EmailDTO mail) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();

        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setSubject(mail.getSubject());
            mimeMessageHelper.setFrom(new InternetAddress(mail.getFrom(), "gmail.com"));
            mimeMessageHelper.setTo(mail.getTo());
            mimeMessageHelper.setText(mail.getContent());

            emailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void sendSimpleMessage(String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);

    }
}