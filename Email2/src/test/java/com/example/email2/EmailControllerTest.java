package com.example.email2;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmailControllerTest {
    @RegisterExtension
    static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP)
            .withConfiguration(GreenMailConfiguration.aConfig().withUser("olv","123456"))
            .withPerMethodLifecycle(false);

    private GreenMail testSmtp;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    public void testSmtpInit(){
        testSmtp = new GreenMail(ServerSetupTest.SMTP);
        testSmtp.start();

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setPort(3025);
        mailSender.setHost("47.149.255.82");
        mailSender.setProtocol("smtp");
        mailSender.setUsername("olv");
        mailSender.setPassword("123456");
    }

    @Test
    public void shouldSendEmail() {
        List<String> list = Arrays.asList("jiaying.li@revature.com", "none", "test");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<String>> request = new HttpEntity<>(list, headers);

        ResponseEntity<Void> response = testRestTemplate.postForEntity("/email", list, Void.class);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());

    }


}
