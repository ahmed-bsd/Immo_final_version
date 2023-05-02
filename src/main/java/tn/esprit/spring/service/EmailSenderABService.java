package tn.esprit.spring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailSenderABService {

    @Autowired
    JavaMailSender mailSender;

    @Async
    public void send(String to, String subj,String body) {

            SimpleMailMessage msg=new SimpleMailMessage();
            msg.setFrom("ahmedbsd1@gmail.com");
            msg.setTo(to);
            msg.setSubject(subj);
            msg.setText(body);
            mailSender.send(msg);
            log.info("email confirmation sent with success");

    }
}
